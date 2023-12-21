/*
package com.example.Envoiler.controller;



import java.text.DecimalFormat;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;




public class Test {

    public String getValuebyMeasuringParameter(String calculation_type, String type, String value, String unit, String parameter) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (parameter != null) {
                MeasuringParameterDTO measuringParameters = objectMapper.readValue(parameter, MeasuringParameterDTO.class);
                System.out.println(measuringParameters.toString());
                switch (type) {

                    case "Generic":
                        return getGenericValueByParameter(measuringParameters);

                    case "Temperature":
                        return getTemperatureByParameter(measuringParameters);

                    case "Humidity":
                        return getHumidityByParameter(measuringParameters);

                    case "Pressure":
                        return getPressureByParameter(measuringParameters);

                    case "Cylindrical Volume":
                        return getCylindricalVolumeByParameter(measuringParameters);

                    case "Length":
                        return getLenghtValueByParameter(measuringParameters);

                    case "Diameter":
                        return getDiameterValueByParameter(measuringParameters);

                    case "Breadth":
                        return getBreadthValueByParameter(measuringParameters);

                    case "Width":
                        return getWidthValueByParameter(measuringParameters);

                    case "Rectangular Volume":
                        return getRectangularVolumeByParameter(measuringParameters);

                    case "Rectangular Area":
                        return getRectangularAreaByParameter(measuringParameters);

                    case "Flow":
                        return getFlowMeterValueByParameter(measuringParameters);

                    case "Voltage":
                        return getVoltmeterValueByParameter(measuringParameters);

                    case "Current":
                        return getCurrentValueByParameter(measuringParameters);

                    case "CO2":
                        return getCO2AnalyserValueByParameter(measuringParameters);

                    case "Light Intensity":
                        return getLightIntensityValueByParameter(measuringParameters);

                    case "Power":
                        return getPowerByParameter(measuringParameters);

                    case "Noise Level":
                        return getNoiseLevelByParameter(measuringParameters);

                    case "VOC":
                        return getVOCAnalyserValueByParameter(measuringParameters);

                    case "Air Quality":
                        return getAirQualityValueByParameter(measuringParameters);

                    case "Electromagnetic Field":
                        return getEMFMeterValueByParameter(measuringParameters);

                    case "Resistance":
                        return getOhmmeterValueByParameter(measuringParameters);

                    case "Capacitance":
                        return getCapacitanceValueByParameter(measuringParameters);

                    case "Speed":
                        return getSpeedByParameter(measuringParameters);

                    case "Acceleration":
                        return getAccelerationByParameter(measuringParameters);

                    case "Level":
                        return getLevelByParameter(measuringParameters);

                    case "Alcohol Content":
                        return getAlcoholContentByParameter(measuringParameters);

                    case "Dissolved Oxygen":
                        return getDissolvedOxygenByParameter(measuringParameters);

                    case "Thickness":
                        return getThicknessByParameter(measuringParameters);

                    case "Electrostatic Field":
                        return getElectrostaticFieldByParameter(measuringParameters);

                    case "Water Salinity":
                        return getWaterSalinityByParameter(measuringParameters);

                    case "Ferrite Content":
                        return getFerriteContentByParameter(measuringParameters);

                    case "Density":
                        return getDensityValueByParameter(measuringParameters);

                    case "Circular Area":
                        return getCircularAreaByParameter(measuringParameters);

                    case "PH":
                        return getPHByParameter(measuringParameters);

                    case "Inductance":
                        return getInductanceByParameter(measuringParameters);

                    case "Water Hardness":
                        return getWaterHardnessByParameter(measuringParameters);

                    case "Ladder":
                        return getLadderLengthbyParameter(measuringParameters);

                    case "Battery":
                        return getBatteryLevelByParameter(measuringParameters);

                    case "Energy Consumption":
                        return getEnergyConsumptionValueByParameter(measuringParameters);

                    case "PM2.5 Level":
                        return getPMLevelValueByParameter(measuringParameters);

                    case "Belt Tension":
                        return getBeltTensionlValueByParameter(measuringParameters);

                    case "Gap Measurement":
                        return getGapMeasurementValueByParameter(measuringParameters);

                    case "Oil Level":
                        return getOilLevelValueByParameter(measuringParameters);

                    case "POE":
                        return getPOEValueByParameter(measuringParameters);

                    case "Surface Roughness":
                        return getSurfaceRoughnessValueByParameter(measuringParameters);

                    case "TDS":
                        return getTDSValueByParameter(measuringParameters);

                    case "Thermal Conductivity":
                        return getThermalConductivityValueByParameter(measuringParameters);

                    case "Tint Measurement":
                        return getTintMeasurementValueByParameter(measuringParameters);

                    case "Torque":
                        return getTorqueValueByParameter(measuringParameters);

                    case "Water Consumption":
                        return getWaterConsumptionValueByParameter(measuringParameters);

                    case "Weight":
                        return getWeightValueByParameter(measuringParameters);

                    case "Digital Tachometer":
                        return getDigitalTachometerValueByParameter(measuringParameters);

                    case "Analog Tachometer":
                        return getAnalogTachometerValueByParameter(measuringParameters);

                    case "Chloride Level":
                        return getChlorideLevelValueByParameter(measuringParameters);

                    case "Sulfate Level":
                        return getSulfateLevelValueByParameter(measuringParameters);

                    case "UV Index":
                        return getUVIndexValueByParameter(measuringParameters);

                    case "Electromagnetic Interference":
                        return getElectromagneticInterferenceValueByParameter(measuringParameters);

                    case "Wood Moisture Content":
                        return getWoodMoistureContentValueByParameter(measuringParameters);

                    case "Belt Frequency":
                        return getBeltFrequencyValueByParameter(measuringParameters);

                    case "Power Factor Analog":
                        return getPowerFactorAnalogValueByParameter(measuringParameters);

                    case "Cable Length":
                        return getCableLengthValueByParameter(measuringParameters);

                    case "Viscosity":
                        return getViscosityValueByParameter(measuringParameters);

                    case "Power Factor Digital":
                        return getPowerFactorDigitalValueByParameter(measuringParameters);

                    case "Differential Pressure":
                        return getDifferentialPressureValueByParameter(measuringParameters);

                    case "Radiation Dose":
                        return getRadiationDoseValueByParameter(measuringParameters);

                    case "Insulation Resistance":
                        return getInsulationResistanceValueByParameter(measuringParameters);

                    case "Temperature 3":
                        return getTemperature3ValueByParameter(measuringParameters);

                    case "Solar Irradiance":
                        return getSolarIrradianceValueByParameter(measuringParameters);

                    case "ORP":
                        return getORPValueByParameter(measuringParameters);

                    case "Impedance":
                        return getImpedanceValueByParameter(measuringParameters);

                    case "Notepad":
                        return getNotepadValueByParameter(measuringParameters);

                    case "SF6 Purity":
                        return getSF6PurityValueByParameter(measuringParameters);

                    case "Force":
                        return getForceValueByParameter(measuringParameters);

                    case "COD":
                        return getCODValueByParameter(measuringParameters);

                    case "Magnetic Permeability":
                        return getMagneticPermeabilityValueByParameter(measuringParameters);

                    case "Chlorine Dioxide Level":
                        return getChlorineDioxideLevelValueByParameter(measuringParameters);

                    case "Surface Tension":
                        return getSurfaceTensionValueByParameter(measuringParameters);

                    case "Vacuum Pressure":
                        return getVacuumPressureValueByParameter(measuringParameters);

                    case "Printer Ink Level":
                        return getPrinterInkPercentageValueByParameter(measuringParameters);

                    case "Interface Status":
                        return getInterfaceStatusByParameter(measuringParameters);

                    case "Flow 1":
                        return getFlowValueByParameter(measuringParameters);

                    case "Energy monitor":
                        return getEnergyConsumptionStatusByOccupancyAndEquipmentParameter(measuringParameters);

                    case "Occupancy Counter":
                        return getCounterValueBySingleCounterInAndOut(measuringParameters);

                    case "Occupancy Counter 1":
                        return getCounterValueByMultipleCounterInAndOut(measuringParameters);

                    case "Light Status":
                        return getLightStatusByParameter(measuringParameters);

                    case "Temperature 4":
                        return getFahrenheitByCelsiusParameter(measuringParameters);
                    case "Communication Status":
                        return getCommunicationStatusByParameter(measuringParameters);

                    case "Configuration Status":
                        return getConfigurationStatusByParameter(measuringParameters);

                    default:
                        return getDefaultByParameter(measuringParameters);
                }
            }
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            System.out.println(e);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            System.out.println(e);
        }
        return null;
    }


    private String getDefaultByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    private String getGenericValueByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getTemperatureByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getHumidityByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getPressureByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getCylindricalVolumeByParameter(MeasuringParameterDTO measuringParameter) {
        try {
            Double diameter = null;
            Double length = null;
            if (measuringParameter.getParameter_1_name() != null && measuringParameter.getParameter_1_name().equals("Diameter")) {
                diameter = Double.parseDouble(measuringParameter.getParameter_1_value());
            }

            if (measuringParameter.getParameter_2_name() != null && measuringParameter.getParameter_2_name().equals("Length")) {
                length = Double.parseDouble(measuringParameter.getParameter_2_value());
            }
            if (length != null && diameter != null) {
                DecimalFormat decimal_format = new DecimalFormat("#.##");
                return String.valueOf(decimal_format.format(3.1415 * length * diameter * diameter / 4));
            }
        } catch (Exception e) {
            System.out.println("FAILED TO CALCULATE CYLINDRICAL VOLUME BY PARAMETER");
        }

        return null;

    }

    private String getLenghtValueByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    private String getDiameterValueByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    private String getBreadthValueByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    private String getWidthValueByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getRectangularAreaByParameter(MeasuringParameterDTO measuringParameter) {

        Double breadth = null;
        Double length = null;

        try {
            if (measuringParameter.getParameter_1_name() != null && measuringParameter.getParameter_1_name().equals("Length")) {
                length = Double.parseDouble(measuringParameter.getParameter_1_value());
            }

            if (measuringParameter.getParameter_2_name() != null && measuringParameter.getParameter_2_name().equals("Breadth")) {
                breadth = Double.parseDouble(measuringParameter.getParameter_2_value());
            }

            if (length != null && breadth != null) {
                DecimalFormat decimal_format = new DecimalFormat("#.##");
                return String.valueOf(decimal_format.format(length * breadth));
            }
        } catch (Exception e) {
            System.out.println("FAILED TO CALCULATE RECTANGULAR VOLUME BY PARAMETER");
        }

        return null;

    }

    public String getRectangularVolumeByParameter(MeasuringParameterDTO measuringParameter) {

        Double breadth = null;
        Double length = null;
        Double width = null;
        try {
            if (measuringParameter.getParameter_1_name() != null && measuringParameter.getParameter_1_name().equals("Length")) {
                length = Double.parseDouble(measuringParameter.getParameter_1_value());
            }

            if (measuringParameter.getParameter_2_name() != null && measuringParameter.getParameter_2_name().equals("Breadth")) {
                breadth = Double.parseDouble(measuringParameter.getParameter_2_value());
            }

            if (measuringParameter.getParameter_3_name() != null && measuringParameter.getParameter_3_name().equals("Width")) {
                width = Double.parseDouble(measuringParameter.getParameter_3_value());
            }

            if (length != null && breadth != null && width != null) {
                DecimalFormat decimal_format = new DecimalFormat("#.##");
                return String.valueOf(decimal_format.format(length * breadth * width));
            }
        } catch (Exception e) {
            System.out.println("FAILED TO CALCULATE RECTANGULAR VOLUME BY PARAMETER");
        }

        return null;

    }

    public String getFlowMeterValueByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getVoltmeterValueByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getCurrentValueByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getCO2AnalyserValueByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getLightIntensityValueByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }


    public String getPowerByParameter(MeasuringParameterDTO measuringParameter) {
        try {
            Double voltage = null;
            Double current = null;
            if (measuringParameter.getParameter_1_name() != null && measuringParameter.getParameter_1_name().equals("Voltage")) {
                voltage = Double.parseDouble(measuringParameter.getParameter_1_value());
            }

            if (measuringParameter.getParameter_2_name() != null && measuringParameter.getParameter_2_name().equals("Current")) {
                current = Double.parseDouble(measuringParameter.getParameter_2_value());
            }
            if (voltage != null && current != null) {
                DecimalFormat decimal_format = new DecimalFormat("#.##");
                return String.valueOf(decimal_format.format(voltage * current));
            }
        } catch (Exception e) {
            System.out.println("FAILED TO CALCULATE CYLINDRICAL VOLUME BY PARAMETER");
        }
        return null;
    }

    public String getNoiseLevelByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getVOCAnalyserValueByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getAirQualityValueByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getEMFMeterValueByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getOhmmeterValueByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getCapacitanceValueByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getSpeedByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getAccelerationByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getLevelByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getAlcoholContentByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getDissolvedOxygenByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getThicknessByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getElectrostaticFieldByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getWaterSalinityByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getFerriteContentByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getDensityValueByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getCircularAreaByParameter(MeasuringParameterDTO measuringParameter) {
        try {
            Double diameter = null;
            if (measuringParameter.getParameter_1_name() != null && measuringParameter.getParameter_1_name().equals("Diameter")) {
                diameter = Double.parseDouble(measuringParameter.getParameter_1_value());
            }

            if (diameter != null) {
                DecimalFormat decimal_format = new DecimalFormat("#.##");
                return String.valueOf(decimal_format.format(3.1415 * diameter * diameter / 4));
            }
        } catch (Exception e) {
            System.out.println("FAILED TO CALCULATE CYLINDRICAL VOLUME BY PARAMETER");
        }

        return null;

    }

    public String getPHByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getInductanceByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getWaterHardnessByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getLadderLengthbyParameter(MeasuringParameterDTO measuringParameter) {
        try {
            Double base_length = null;
            if (measuringParameter.getParameter_1_name() != null && measuringParameter.getParameter_1_name().equals("Length")) {
                base_length = Double.parseDouble(measuringParameter.getParameter_1_value()) / Math.sqrt(Double.parseDouble("17"));
                if (base_length != null) {
                    DecimalFormat decimal_format = new DecimalFormat("#.##");
                    return String.valueOf(decimal_format.format(base_length));
                }
            }
        } catch (Exception e) {
            System.out.println("FAILED TO CALCULATE LADDER LENGTH BY PARAMETER");
        }
        return null;
    }

    public String getBatteryLevelByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    public String getEnergyConsumptionValueByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    private String getPMLevelValueByParameter(MeasuringParameterDTO measuringParameter) {
        return measuringParameter.getParameter_1_value();
    }

    private String getBeltTensionlValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getGapMeasurementValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getOilLevelValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getPOEValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getSurfaceRoughnessValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getTDSValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getThermalConductivityValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getTintMeasurementValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getTorqueValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getWaterConsumptionValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getWeightValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getDigitalTachometerValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getAnalogTachometerValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getChlorideLevelValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getSulfateLevelValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getUVIndexValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getElectromagneticInterferenceValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getWoodMoistureContentValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getBeltFrequencyValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getPowerFactorAnalogValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getCableLengthValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getViscosityValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getPowerFactorDigitalValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getDifferentialPressureValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getRadiationDoseValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getInsulationResistanceValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getTemperature3ValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getSolarIrradianceValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getORPValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getImpedanceValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getNotepadValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getSF6PurityValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getForceValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getCODValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getMagneticPermeabilityValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getChlorineDioxideLevelValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getSurfaceTensionValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getVacuumPressureValueByParameter(MeasuringParameterDTO measuringParameters) {
        return measuringParameters.getParameter_1_value();
    }

    private String getPrinterInkPercentageValueByParameter(MeasuringParameterDTO measuringParameters) {
        try {
            if (measuringParameters.getParameter_1_name() != null && measuringParameters.getParameter_1_name().equals("Maximum Capacity") &&
                    measuringParameters.getParameter_2_name() != null && measuringParameters.getParameter_2_name().equals("Current Capacity")) {
                if (measuringParameters.getParameter_1_value() != null && measuringParameters.getParameter_2_value() != null) {
                    Double percentage_value = Double.parseDouble(measuringParameters.getParameter_2_value()) / Double.parseDouble(measuringParameters.getParameter_1_value()) * 100;

                    if (percentage_value != null) {
                        DecimalFormat decimal_format = new DecimalFormat("#.##");
                        return String.valueOf(decimal_format.format(percentage_value));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("FAILED TO CALCULATE PERCENTAGE BY PARAMETER");
        }
        return null;
    }

    private String getInterfaceStatusByParameter(MeasuringParameterDTO measuringParameters) {
        if (measuringParameters.getParameter_1_value() != null) {
            switch (measuringParameters.getParameter_1_value()) {
                case "1":
                    return "Up";
                case "2":
                    return "Down";
                case "3":
                    return "Testing";
                case "4":
                    return "Unknown";
                case "5":
                    return "Dormant";
                case "6":
                    return "Not Present";
                case "7":
                    return "Lower Layer Down";
                default:
                    return "Not Available";
            }
        }
        return null;
    }

    private String getFlowValueByParameter(MeasuringParameterDTO measuringParameters) {
        if (measuringParameters.getParameter_1_name() != null && measuringParameters.getParameter_1_name().equals("Pulse Count") &&
                measuringParameters.getParameter_2_name() != null && measuringParameters.getParameter_2_name().equals("K-Factor")
        ) {
            Double value = Double.parseDouble(measuringParameters.getParameter_2_value()) * Double.parseDouble(measuringParameters.getParameter_1_value());
            Double offset = 0.0;
            if (measuringParameters.getParameter_3_name() != null && measuringParameters.getParameter_3_name().equalsIgnoreCase("Offset")) {
                if (measuringParameters.getParameter_3_value() != null && !measuringParameters.getParameter_3_value().isEmpty()) {
                    offset = Double.parseDouble(measuringParameters.getParameter_3_value());
                }
            }
            if (value != null && value > 0) {
                value = value + offset;
            }
            if (value != null) {
                DecimalFormat decimal_format = new DecimalFormat("#.##");
                return String.valueOf(decimal_format.format(value));
            }
        }
        return null;
    }

    private String getEnergyConsumptionStatusByOccupancyAndEquipmentParameter(MeasuringParameterDTO measuringParameters) {
        if (measuringParameters.getParameter_1_name() != null && measuringParameters.getParameter_1_name().equalsIgnoreCase("Occupancy Status") &&
                measuringParameters.getParameter_2_name() != null && measuringParameters.getParameter_2_name().equalsIgnoreCase("Equipment Status")) {
            if (measuringParameters.getParameter_1_value() != null && measuringParameters.getParameter_1_value().equalsIgnoreCase("inactive") &&
                    measuringParameters.getParameter_2_value() != null && measuringParameters.getParameter_2_value().equalsIgnoreCase("active")) {
                return "Wasted";
            }
            return "Normal";
        }
        return null;
    }

    private String getCounterValueBySingleCounterInAndOut(MeasuringParameterDTO measuringParameters) {
        if (measuringParameters.getParameter_1_name() != null && measuringParameters.getParameter_1_name().equalsIgnoreCase("Counter In") &&
                measuringParameters.getParameter_2_name() != null && measuringParameters.getParameter_2_name().equalsIgnoreCase("Counter Out")) {
            if (measuringParameters.getParameter_1_value() != null && measuringParameters.getParameter_2_value() != null) {
                Double value = Double.parseDouble(measuringParameters.getParameter_1_value()) - Double.parseDouble(measuringParameters.getParameter_2_value());
                if (value != null) {
                    return String.valueOf(value.intValue());
                }
            }
        }
        return null;
    }

    private String getCounterValueByMultipleCounterInAndOut(MeasuringParameterDTO measuringParameters) {
        if (measuringParameters.getParameter_1_name() != null && measuringParameters.getParameter_1_name().equalsIgnoreCase("Counter In-1") &&
                measuringParameters.getParameter_2_name() != null && measuringParameters.getParameter_2_name().equalsIgnoreCase("Counter Out-1") &&
                measuringParameters.getParameter_3_name() != null && measuringParameters.getParameter_3_name().equalsIgnoreCase("Counter In-2") &&
                measuringParameters.getParameter_4_name() != null && measuringParameters.getParameter_4_name().equalsIgnoreCase("Counter Out-2")) {
            if (measuringParameters.getParameter_1_value() != null && measuringParameters.getParameter_2_value() != null && measuringParameters.getParameter_3_value() != null && measuringParameters.getParameter_4_value() != null) {
                Double value = (Double.parseDouble(measuringParameters.getParameter_1_value()) - Double.parseDouble(measuringParameters.getParameter_2_value())) +
                        (Double.parseDouble(measuringParameters.getParameter_3_value()) - Double.parseDouble(measuringParameters.getParameter_4_value()));
                if (value != null) {
                    return String.valueOf(value.intValue());
                }
            }
        }
        return null;
    }

    private String getLightStatusByParameter(MeasuringParameterDTO measuringParameters) {
        if (measuringParameters.getParameter_1_name() != null && measuringParameters.getParameter_1_name().equalsIgnoreCase("Light Intensity")) {
            if (measuringParameters.getParameter_1_value() != null) {
                if (!measuringParameters.getParameter_1_value().isEmpty() && Double.parseDouble(measuringParameters.getParameter_1_value()) > 0.0) {
                    return "ON";
                }
                return "OFF";
            }
        }
        return null;
    }

    private String getFahrenheitByCelsiusParameter(MeasuringParameterDTO measuringParameters) {
        if( measuringParameters.getParameter_1_name() != null &&  measuringParameters.getParameter_1_name().equalsIgnoreCase("Temperature") && measuringParameters.getParameter_1_value() != null) {
            Double value = (Double.parseDouble(measuringParameters.getParameter_1_value()) * 9/5)+ 32;
            if(value != null) {
                DecimalFormat decimal_format = new DecimalFormat("#.##");
                return String.valueOf(decimal_format.format(value));
            }
        }
        return null;
    }


    private String getConfigurationStatusByParameter(MeasuringParameterDTO measuringParameters) {
        if( measuringParameters.getParameter_1_name() != null &&  measuringParameters.getParameter_1_name().equalsIgnoreCase("Failure Since") && measuringParameters.getParameter_1_value() != null) {
            if(Double.parseDouble(measuringParameters.getParameter_1_value()) > 0){
                return "Faulty";
            }
            return "Normal";
        }
        return null;
    }


    private String getCommunicationStatusByParameter(MeasuringParameterDTO measuringParameters) {
        if( measuringParameters.getParameter_1_name() != null &&  measuringParameters.getParameter_1_name().equalsIgnoreCase("Failure Since") && measuringParameters.getParameter_1_value() != null) {
            if(Double.parseDouble(measuringParameters.getParameter_1_value()) > 0){
                return "Faulty";
            }
            return "Normal";
        }
        return null;
    }

}

*/
