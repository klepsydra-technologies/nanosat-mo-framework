/* ----------------------------------------------------------------------------
 * Copyright (C) 2015      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : ESA NanoSat MO Framework
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an "as is" basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * ----------------------------------------------------------------------------
 */
package esa.mo.mc.impl.provider;

import esa.mo.com.impl.util.COMServicesProvider;
import esa.mo.com.impl.util.HelperArchive;
import esa.mo.com.impl.util.HelperCOM;
import esa.mo.mc.impl.interfaces.ParameterStatusListener;
import esa.mo.helpertools.connections.SingleConnectionDetails;
import esa.mo.helpertools.helpers.HelperTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.com.archive.structures.ArchiveDetails;
import org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList;
import org.ccsds.moims.mo.com.structures.ObjectDetails;
import org.ccsds.moims.mo.com.structures.ObjectId;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.BooleanList;
import org.ccsds.moims.mo.mal.structures.ElementList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mc.parameter.ParameterHelper;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterConversion;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterDefinitionDetails;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterDefinitionDetailsList;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterValue;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterValueList;
import org.ccsds.moims.mo.mc.structures.ParameterExpression;

/**
 *
 * @author Cesar Coelho
 */
public class ParameterManager extends DefinitionsManager {

    private final transient ConversionServiceImpl conversionService = new ConversionServiceImpl();
    private static final transient int SAVING_PERIOD = 20;  // Used to store the uniqueObjIdPVal only once every "SAVINGPERIOD" times

    private Long uniqueObjIdDef; // Counter (different for every Definition)
    private Long uniqueObjIdPVal;
    private final transient ParameterStatusListener parametersMonitoring;   // transient: marks members that won't be serialized.

    public ParameterManager (COMServicesProvider comServices, ParameterStatusListener parametersMonitoring) {
        super(comServices);

            try {
                ParameterHelper.init(MALContextFactory.getElementFactoryRegistry());
            } catch (MALException ex) { // nothing to be done..
            }

        this.parametersMonitoring = parametersMonitoring;

        if (super.getArchiveService() == null) {  // No Archive?
            this.uniqueObjIdDef = new Long(0); // The zeroth value will not be used (reserved for the wildcard)
            this.uniqueObjIdPVal = new Long(0); // The zeroth value will not be used (reserved for the wildcard)
//            this.load(); // Load the file
        } else { 
            // With Archive...
            
            // Initialize the Conversion service
            try {
                this.conversionService.init(super.getArchiveService());
            } catch (MALException ex) {
                Logger.getLogger(ParameterManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    protected ParameterDefinitionDetailsList getAll() {
        return (ParameterDefinitionDetailsList) this.getAllDefs();
    }

    @Override
    protected Boolean compareName(Long objId, Identifier name) {
        return this.get(objId).getName().equals(name);
    }

    @Override
    protected ElementList newDefinitionList() {
        return new ParameterDefinitionDetailsList();
    }

    protected ParameterDefinitionDetails get(Long input) {
        return (ParameterDefinitionDetails) this.getDefs().get(input);
    }

    /**
     *
     * @param pVal
     * @param related
     * @param connectionDetails
     * @return The unique identifier or null if the implementation is using the
     * Archive service for objects storage. In this case, the unique identifier
     * must be retrieved from the Archive during storage
     */
    protected Long storeAndGeneratePValobjId(ParameterValue pVal, Long related, SingleConnectionDetails connectionDetails) {
        if (super.getArchiveService() == null) {
            uniqueObjIdPVal++;
            // It is used to avoid constant saving every time we generate a new obj Inst identifier.
            if (uniqueObjIdPVal % SAVING_PERIOD == 0) {
//                this.save();
            }
            return this.uniqueObjIdPVal;
        } else {
            ParameterValueList pValList = new ParameterValueList();
            pValList.add(pVal);

            try {  // requirement: 3.3.4.2
                LongList objIds = super.getArchiveService().store(
                        true,
                        ParameterHelper.PARAMETERVALUEINSTANCE_OBJECT_TYPE,
                        connectionDetails.getDomain(),
                        HelperArchive.generateArchiveDetailsList(related, null, connectionDetails),
                        pValList,
                        null);

                if (objIds.size() == 1) {
                    return objIds.get(0);
                }

            } catch (MALException ex) {
                Logger.getLogger(ParameterManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MALInteractionException ex) {
                Logger.getLogger(ParameterManager.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }

    /**
     *
     * @param pVal
     * @param relatedList
     * @param connectionDetails
     * @return The unique identifier or null if the implementation is using the
     * Archive service for objects storage. In this case, the unique identifier
     * must be retrieved from the Archive during storage
     */
    protected LongList storeAndGenerateMultiplePValobjId(ParameterValueList pVal, LongList relatedList, SingleConnectionDetails connectionDetails) {
        if (this.getArchiveService() != null) {

            ArchiveDetailsList archiveDetailsList = new ArchiveDetailsList();

            for (Long related : relatedList) {
                ArchiveDetails archiveDetails = new ArchiveDetails();
                archiveDetails.setInstId(new Long(0));
                archiveDetails.setDetails(new ObjectDetails(related, null));
                archiveDetails.setNetwork(connectionDetails.getConfiguration().getNetwork());
                archiveDetails.setTimestamp(HelperTime.getTimestamp());
                archiveDetails.setProvider(connectionDetails.getProviderURI());

                archiveDetailsList.add(archiveDetails);
            }

            try {  // requirement: 3.3.4.2
                LongList objIds = this.getArchiveService().store(
                        true,
                        ParameterHelper.PARAMETERVALUEINSTANCE_OBJECT_TYPE,
                        connectionDetails.getDomain(),
                        archiveDetailsList,
                        pVal,
                        null);

                if (objIds.size() == pVal.size()) {
                    return objIds;
                }

            } catch (MALException ex) {
                Logger.getLogger(ParameterManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MALInteractionException ex) {
                Logger.getLogger(ParameterManager.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }

        return null;

    }
    
    
    
    protected ParameterValueList getParameterValues(LongList input) {
        ParameterValueList pValList = new ParameterValueList();
        for (Long input1 : input) {
            try {
                pValList.add(getParameterValue(input1));
            } catch (MALInteractionException ex) {
                pValList.add(null);
            }
        }

        return pValList;
    }

    public ParameterValue getParameterValue(Long objId) throws MALInteractionException {
        if (!this.exists(objId)) {  // The Parameter does not exist
            throw new MALInteractionException(new MALStandardError(MALHelper.UNKNOWN_ERROR_NUMBER, objId));

            /*
            ParameterValue pVal = new ParameterValue();
            pVal.setValid(false);
            pVal.setInvalidSubState(new UOctet((short) 1)); // 1: EXPIRED
            pVal.setConvertedValue(null);
            pVal.setRawValue(null);
            return pVal;
            */
        }

        ParameterDefinitionDetails pDef = this.get(objId);
        Attribute rawValue;
        
        if (parametersMonitoring != null){
            rawValue = parametersMonitoring.onGetValue(pDef.getName(), pDef.getRawType());
        }else{
            rawValue = null;
        }
        
        Attribute convertedValue;

        // Is the Conversion service available for use?
        if (conversionService != null) {
            convertedValue = conversionService.generateConvertedValue(rawValue, pDef.getConversion());
        } else {
            convertedValue = null;
        }

        UOctet invalidSubState = this.generateInvalidSubState(pDef, rawValue, convertedValue);

        if (invalidSubState.equals(new UOctet((short) 4))
                || invalidSubState.equals(new UOctet((short) 5))
                || invalidSubState.equals(new UOctet((short) 2))) {
            convertedValue = null;  // requirement: 3.3.2.24
        }

        Attribute unionConvertedValue = (convertedValue == null) ? null : convertedValue;  // Union doesn't directly accept null values

        // Generate final Parameter Value
        return new ParameterValue((invalidSubState.getValue() == 0),
                invalidSubState, rawValue, unionConvertedValue);
    }

/*    
    public static Boolean evaluateExpression13234325(ParameterExpression expression, Attribute value, Attribute convertedValue){

        if (expression.getUseConverted()){ // Is the validity checking for the converted or for the raw value?
            value = convertedValue;
        }

        Attribute parameterIdValue = value;
        Boolean eval = HelperCOM.evaluateExpression(parameterIdValue, expression.getOperator(), expression.getValue());

        return eval;
    }
*/
    
    public Boolean evaluateParameterExpression(ParameterExpression expression){

        if (expression == null) {
            return true;  // No test is required
        }

        ParameterDefinitionDetails pDef = this.get(expression.getParameterId().getInstId());
        Attribute value = parametersMonitoring.onGetValue(pDef.getName(), pDef.getRawType());
        
        if (expression.getUseConverted()){ // Is the validity checking for the converted or for the raw value?
            value = conversionService.generateConvertedValue(value, pDef.getConversion());
        }

        return HelperCOM.evaluateExpression(value, expression.getOperator(), expression.getValue());
    }
    
    
    protected UOctet generateInvalidSubState(ParameterDefinitionDetails definition, Attribute value, Attribute convertedValue) {
        if (definition == null) // Does not exist?
        {
            return new UOctet((short) 4); // UNVERIFIED
        }
        // Figure 3-2: Flow Chart for Determining the Validity of a Parameter (page 38)
        final ParameterExpression validityExpression = definition.getValidityExpression();

        if (validityExpression == null) // requirement: 3.3.2.19
        {
            return new UOctet((short) 0); // VALID
        }
        // The code continues... (requirement: 3.3.2.18)

        ParameterDefinitionDetails paramValidityDefinition = this.get(definition.getValidityExpression().getParameterId().getInstId());
        if (paramValidityDefinition == null) // The validity parameter does not exist?
        {
            return new UOctet((short) 4); // UNVERIFIED
        }

/*        
        if (validityExpression.getUseConverted()) // Is the validity checking for the converted or for the raw value?
        {
            value = convertedValue;
        }

        Attribute parameterIdValue = value;
        Boolean eval = HelperCOM.evaluateExpression13234325(parameterIdValue, validityExpression.getOperator(), validityExpression.getValue());
*/

//        Boolean eval = ParameterManager.evaluateExpression13234325(validityExpression, value, convertedValue);
        Boolean eval = this.evaluateParameterExpression(validityExpression);

        if (eval == null){ // The expression was not evaluated?
            return new UOctet((short) 4); // UNVERIFIED
        }//            return Validity.UNVERIFIED; // requirement: 3.3.2.20

        if (!eval){ // Is the validity expression false?
            return new UOctet((short) 5); // INVALID
        }//            return Validity.INVALID;  // requirement: 3.3.2.21

        ParameterConversion conversion = paramValidityDefinition.getConversion();

        if (conversion == null){ // There's no conversion to apply?
            return new UOctet((short) 0); // VALID
        }//            return Validity.VALID;  // requirement: 3.3.2.22

        // The conversion failed?
        if (convertedValue == null) {
            return new UOctet((short) 2); // INVALID_RAW
        }//            return Validity.VALID_RAW_ONLY;  // requirement: 3.3.2.23

        return new UOctet((short) 0); // VALID
//        return Validity.VALID;

    }

    /*    
     private Validity generateValidityState(ParameterDefinitionDetails definition, Boolean successfulConversion){
     if (definition == null)  // Does not exist?
     return Validity.UNVERIFIED;
        
     // Figure 3-2: Flow Chart for Determining the Validity of a Parameter (page 38)
     final ParameterExpression validityExpression = definition.getValidityExpression();
        
     if (validityExpression == null) // requirement: 3.3.2.19
     return Validity.VALID;
        
     // The code continues... (requirement: 3.3.2.18)

     ParameterDefinitionDetails paramValidityDefinition = (ParameterDefinitionDetails) this.get(definition.getValidityExpression().getParameterId().getInstId());
     if (paramValidityDefinition == null)  // The validity parameter does not exist?
     return Validity.UNVERIFIED;

     Attribute value = (parametersMonitoring.acquire(paramValidityDefinition.getName(), paramValidityDefinition.getRawType() ));

     if (validityExpression.getUseConverted())  // Is the validity checking for the converted or for the raw value?
     value = generateConvertedValue(value, paramValidityDefinition.getConversion());

     Attribute parameterIdValue = value;
     Boolean eval = evaluateExpression13234325 (parameterIdValue, validityExpression.getOperator(), validityExpression.getValue());

     if (eval == null)  // The expression was not evaluated?
     return Validity.UNVERIFIED; // requirement: 3.3.2.20
        
     if (!eval)  // Is the validity expression false? 
     return Validity.INVALID;  // requirement: 3.3.2.21

     ParameterConversion conversion = paramValidityDefinition.getConversion();
        
     if (conversion == null) // There's no conversion to apply?
     return Validity.VALID;  // requirement: 3.3.2.22
            
     // The conversion failed?
     if (!successfulConversion)
     return Validity.VALID_RAW_ONLY;  // requirement: 3.3.2.23
            
        
     return Validity.VALID;
        
     }
     */

    protected Long add(ParameterDefinitionDetails definition, ObjectId source, SingleConnectionDetails connectionDetails) { // requirement: 3.3.2.5
        definition.setGenerationEnabled(false);  // requirement: 3.3.2.7

        if (super.getArchiveService() == null) {
            uniqueObjIdDef++; // This line as to go before any writing (because it's initialized as zero and that's the wildcard)
            this.addDef(uniqueObjIdDef, definition);
//            this.save();
            return uniqueObjIdDef;
        } else {
            ParameterDefinitionDetailsList defs = new ParameterDefinitionDetailsList();
            defs.add(definition);

            try {
                LongList objIds = super.getArchiveService().store(
                        true,
                        ParameterHelper.PARAMETERDEFINITION_OBJECT_TYPE,
                        connectionDetails.getDomain(),
                        HelperArchive.generateArchiveDetailsList(null, source, connectionDetails),
                        defs,
                        null);

                if (objIds.size() == 1) {
                    this.addDef(objIds.get(0), definition);
                    return objIds.get(0);
                }

            } catch (MALException ex) {
                Logger.getLogger(ParameterManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MALInteractionException ex) {
                Logger.getLogger(ParameterManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;

    }

    protected boolean update(Long objId, ParameterDefinitionDetails definition, SingleConnectionDetails connectionDetails) { // requirement: 3.3.2.5
        Boolean success = this.updateDef(objId, definition);

        if (super.getArchiveService() != null) {  // It should also update on the COM Archive
            try {
                ParameterDefinitionDetailsList defs = new ParameterDefinitionDetailsList();
                defs.add(definition);

                ArchiveDetails archiveDetails = HelperArchive.getArchiveDetailsFromArchive(super.getArchiveService(), 
                        ParameterHelper.PARAMETERDEFINITION_OBJECT_TYPE, connectionDetails.getDomain(), objId);

                ArchiveDetailsList archiveDetailsList = new ArchiveDetailsList();
                archiveDetailsList.add(archiveDetails);

                super.getArchiveService().update(
                        ParameterHelper.PARAMETERDEFINITION_OBJECT_TYPE,
                        connectionDetails.getDomain(),
                        archiveDetailsList,
                        defs,
                        null);

            } catch (MALException ex) {
                Logger.getLogger(ParameterManager.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } catch (MALInteractionException ex) {
                Logger.getLogger(ParameterManager.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }

//        this.save();
        return success;
    }

    protected boolean delete(Long objId) { // requirement: 3.3.2.5
        if (!this.deleteDef(objId)) {
            return false;
        }
//        this.save();
        return true;
    }

    protected boolean setGenerationEnabled(Long objId, Boolean bool, SingleConnectionDetails connectionDetails) { // requirement: 3.3.2.5
        ParameterDefinitionDetails def = (ParameterDefinitionDetails) this.getDefs().get(objId);
        
        if (def == null){
            return false;
        }
        
        if (def.getGenerationEnabled().booleanValue() == bool){ // Is it set with the requested value already?
            return false; // the value was not changed
        }
        def.setGenerationEnabled(bool);
        this.update(objId, def, connectionDetails);
        return true;
    }

    protected void setGenerationEnabledAll(Boolean bool, SingleConnectionDetails connectionDetails) {
        LongList objIds = new LongList(); 
        objIds.addAll(this.getDefs().keySet());
        
        for (Long objId : objIds) {
            ParameterDefinitionDetails def = this.get(objId);
            def.setGenerationEnabled(bool);
            this.update(objId, def, connectionDetails);
        }

    }

    protected BooleanList setValues(LongList paramDefInstIds, ParameterValueList newValues) {

        Long objId;
        ParameterValue newValue;
        
        BooleanList successFlags = new BooleanList();
        
        for (int i = 0; i < paramDefInstIds.size(); i++) {
            objId = paramDefInstIds.get(i);
            newValue = newValues.get(i);
            Boolean success;
            
            if(parametersMonitoring != null){
                success = parametersMonitoring.onSetValue(this.get(objId).getName(), newValue.getRawValue());
            }else{
                success = false;
            }
            
            successFlags.add(success);
            
        }

        return successFlags;
    }

}