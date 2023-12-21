package com.example.Envoiler.service;

import com.example.Envoiler.Repo.GeneralRepo;
import com.example.Envoiler.util.MarkerChecker;
import com.google.gson.*;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EnvService {

    private final GeneralRepo generalRepo;
    MarkerChecker markerChecker = new MarkerChecker();

    public EnvService(GeneralRepo generalRepo) {
        this.generalRepo = generalRepo;
    }

    private  void updateDataPoint(JSONObject dataPoint, String[] markersList) throws JSONException {
        JSONObject metadataProtoSpec = dataPoint.getJSONObject("metadata_proto_spec");
        JSONObject rawData = metadataProtoSpec.getJSONObject("raw_data");
        JSONObject equipment = metadataProtoSpec.getJSONObject("equipment");

        if (rawData.has("measuring_instrument_type")) {
            String instrumentType = rawData.getString("measuring_instrument_type");
            System.out.println("Instrument Type: " + instrumentType);

            setMarker(rawData);

            setEnumType(instrumentType, rawData);
            rawData.remove("measuring_instrument_type");


        }

        if (equipment.has("sclera_point_equipment_category")) {
            String categoryValue = equipment.getString("sclera_point_equipment_category").trim();

            String matchedMarker = getBestMatchingMarker(categoryValue, markersList);

//            if (containsCategoryValue(markersList, categoryValue)) {
//                rawData.put("fan", ":m");
//            }

            rawData.put("sensor", ":m");
            // You can use the matchedMarker value as needed

        }
    }

    private JSONObject setMarker(JSONObject rawData) throws JSONException {
        System.out.println(rawData.get("dis"));
        String displayName = (String) rawData.get("dis");
        String[] markerList = displayName.split(" ");

        String marker = markerChecker.isMarkerChecker(markerList);
        if(marker!=null){
            rawData.put(marker,":m");
        }




        // Displaying the split words
     /*   for (String marker : markerList) {
            if(marker.equalsIgnoreCase(""))
            if (marker.equalsIgnoreCase("Light") || marker.equalsIgnoreCase("Heating") || marker.equalsIgnoreCase("Temperature") ||
                    marker.equalsIgnoreCase("Energy") || marker.equalsIgnoreCase("Cooling") ||
            marker.equalsIgnoreCase("Max")||marker.equalsIgnoreCase("Min")||marker.equalsIgnoreCase("Avg")) {
                rawData.put(marker.toLowerCase(), ":m");
            }
        }*/
        return rawData;
    }

    private static JSONObject setEnumType(String instrumentType, JSONObject rawData) {
        String filePath = "src/main/resources/enum_sample.json";
        try (FileReader reader = new FileReader(filePath)) {

            Gson gson = new Gson();


            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            JsonArray enumList = (JsonArray) jsonObject.get("enum_List");
            // System.out.println(enumList.get(i));
            for (int i = 0; i < enumList.size(); i++) {
                System.out.println(enumList.get(i));
                JsonObject enumType = (JsonObject) enumList.get(i);

                if ((enumType.get("type").toString().replaceAll("\"", "")).equals(instrumentType)) {

                    String enumValue = String.valueOf(enumType.get("enum"));

                    JSONObject newEnum = new JSONObject(enumValue);

                    rawData.put("enum", newEnum);
                    rawData.put("kind", "enum");


                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return rawData;
    }

    private static String getBestMatchingMarker(String value, String[] markersList) {

        String mostSimilarMarker = null;
        double highestScore = 0.0;

        for (String marker : markersList) {
            double score = FuzzySearch.ratio(marker.trim(), value.trim());
            if (score > highestScore) {
                highestScore = score;
                mostSimilarMarker = marker;
            }
        }

        assert mostSimilarMarker != null;
        return mostSimilarMarker.trim();
    }

    public List<String> getResponse(String assetId) throws IOException, JSONException {

         List<String> jsonFromDataBase = generalRepo.getJSONResponse(assetId);
//        for(int i =0; i<jsonFromDataBase.size();i++){
//           // jsonFromDataBase.add(i,"{\"datapoints\": [{\"enum\": [], \"hidden\": false, \"tagged\": false, \"point_id\": \"5f51cdea-5e73-11ed-a1de-ed2fb742e341\", \"read_only\": true, \"absolute_address\": \"sclera://vmds/VDMS931/asset/000d6f000ae63c08/sensor/5f51cdea-5e73-11ed-a1de-ed2fb742e341\", \"protocol_specific\": {\"struct_version\": 20}, \"metadata_proto_spec\": {\"raw_data\": {\"dis\": \"Communication Status\", \"unit\": null, \"curVal\": \"Normal\", \"sclera_locations\": [{\"val\": \"160b7530-1187-11ed-aa44-2741238d9b18\", \"_kind\": \"ref\"}], \"sclera_attributes\": [{\"name\": \"Failure Since\", \"unit\": \"\", \"value\": \"0.0\"}]}, \"equipment\": {\"id\": \"000d6f000ae63c08\", \"type\": \"lighting_controller\", \"unique_name\": \"000d6f000ae63c08 (375303DP231-375303DP246)\", \"sclera_point_equipment_category\": \"generic\", \"sclera_point_equipment_floor_ref\": \"8f4451cd-a0a5-11ec-bc72-3d5e4a373e02\", \"sclera_point_equipment_location_ref\": \"160b7530-1187-11ed-aa44-2741238d9b18\"}, \"raw_data_base64\": null}, \"reference_point_name\": \"Communication Status\", \"device_object_data_type\": null}, {\"enum\": [], \"hidden\": false, \"tagged\": false, \"point_id\": \"6ce99292-5e73-11ed-a1de-036b07b2e4f5\", \"read_only\": true, \"absolute_address\": \"sclera://vmds/VDMS931/asset/000d6f000ae63c08/sensor/6ce99292-5e73-11ed-a1de-036b07b2e4f5\", \"protocol_specific\": {\"struct_version\": 20}, \"metadata_proto_spec\": {\"raw_data\": {\"dis\": \"Dimming Light\", \"unit\": \"%\", \"curVal\": \"0.0\", \"sclera_locations\": [{\"val\": \"160b7530-1187-11ed-aa44-2741238d9b18\", \"_kind\": \"ref\"}], \"sclera_attributes\": [{\"name\": \"Light Intensity\", \"unit\": \"\", \"value\": \"0.0\"}]}, \"equipment\": {\"id\": \"000d6f000ae63c08\", \"type\": \"lighting_controller\", \"unique_name\": \"000d6f000ae63c08 (375303DP231-375303DP246)\", \"sclera_point_equipment_category\": \"generic\", \"sclera_point_equipment_floor_ref\": \"8f4451cd-a0a5-11ec-bc72-3d5e4a373e02\", \"sclera_point_equipment_location_ref\": \"160b7530-1187-11ed-aa44-2741238d9b18\"}, \"raw_data_base64\": null}, \"reference_point_name\": \"Dimming Light\", \"device_object_data_type\": null}, {\"enum\": [], \"hidden\": false, \"tagged\": false, \"point_id\": \"70c8a1a9-5e73-11ed-a1de-571d6841ca53\", \"read_only\": true, \"absolute_address\": \"sclera://vmds/VDMS931/asset/000d6f000ae63c08/sensor/70c8a1a9-5e73-11ed-a1de-571d6841ca53\", \"protocol_specific\": {\"struct_version\": 20}, \"metadata_proto_spec\": {\"raw_data\": {\"dis\": \"Energy Usage\", \"unit\": \"kWh\", \"curVal\": \"0.0023508333\", \"sclera_locations\": [{\"val\": \"160b7530-1187-11ed-aa44-2741238d9b18\", \"_kind\": \"ref\"}], \"sclera_attributes\": [{\"name\": \"Energy Consumption\", \"unit\": \"\", \"value\": \"0.0023508333\"}]}, \"equipment\": {\"id\": \"000d6f000ae63c08\", \"type\": \"lighting_controller\", \"unique_name\": \"000d6f000ae63c08 (375303DP231-375303DP246)\", \"sclera_point_equipment_category\": \"generic\", \"sclera_point_equipment_floor_ref\": \"8f4451cd-a0a5-11ec-bc72-3d5e4a373e02\", \"sclera_point_equipment_location_ref\": \"160b7530-1187-11ed-aa44-2741238d9b18\"}, \"raw_data_base64\": null}, \"reference_point_name\": \"Energy Usage\", \"device_object_data_type\": null}, {\"enum\": [], \"hidden\": false, \"tagged\": false, \"point_id\": \"7453eb75-5e73-11ed-a1de-63242f0807ee\", \"read_only\": true, \"absolute_address\": \"sclera://vmds/VDMS931/asset/000d6f000ae63c08/sensor/7453eb75-5e73-11ed-a1de-63242f0807ee\", \"protocol_specific\": {\"struct_version\": 20}, \"metadata_proto_spec\": {\"raw_data\": {\"dis\": \"Configuration Status\", \"unit\": null, \"curVal\": \"Normal\", \"sclera_locations\": [{\"val\": \"160b7530-1187-11ed-aa44-2741238d9b18\", \"_kind\": \"ref\"}], \"sclera_attributes\": [{\"name\": \"Failure Since\", \"unit\": \"\", \"value\": \"0.0\"}]}, \"equipment\": {\"id\": \"000d6f000ae63c08\", \"type\": \"lighting_controller\", \"unique_name\": \"000d6f000ae63c08 (375303DP231-375303DP246)\", \"sclera_point_equipment_category\": \"generic\", \"sclera_point_equipment_floor_ref\": \"8f4451cd-a0a5-11ec-bc72-3d5e4a373e02\", \"sclera_point_equipment_location_ref\": \"160b7530-1187-11ed-aa44-2741238d9b18\"}, \"raw_data_base64\": null}, \"reference_point_name\": \"Configuration Status\", \"device_object_data_type\": null}]}{\"datapoints\": [{\"enum\": [], \"hidden\": false, \"tagged\": false, \"point_id\": \"5f51cdea-5e73-11ed-a1de-ed2fb742e341\", \"read_only\": true, \"absolute_address\": \"sclera://vmds/VDMS931/asset/000d6f000ae63c08/sensor/5f51cdea-5e73-11ed-a1de-ed2fb742e341\", \"protocol_specific\": {\"struct_version\": 20}, \"metadata_proto_spec\": {\"raw_data\": {\"dis\": \"Communication Status\", \"unit\": null, \"curVal\": \"Normal\", \"sclera_locations\": [{\"val\": \"160b7530-1187-11ed-aa44-2741238d9b18\", \"_kind\": \"ref\"}], \"sclera_attributes\": [{\"name\": \"Failure Since\", \"unit\": \"\", \"value\": \"0.0\"}]}, \"equipment\": {\"id\": \"000d6f000ae63c08\", \"type\": \"lighting_controller\", \"unique_name\": \"000d6f000ae63c08 (375303DP231-375303DP246)\", \"sclera_point_equipment_category\": \"generic\", \"sclera_point_equipment_floor_ref\": \"8f4451cd-a0a5-11ec-bc72-3d5e4a373e02\", \"sclera_point_equipment_location_ref\": \"160b7530-1187-11ed-aa44-2741238d9b18\"}, \"raw_data_base64\": null}, \"reference_point_name\": \"Communication Status\", \"device_object_data_type\": null}, {\"enum\": [], \"hidden\": false, \"tagged\": false, \"point_id\": \"6ce99292-5e73-11ed-a1de-036b07b2e4f5\", \"read_only\": true, \"absolute_address\": \"sclera://vmds/VDMS931/asset/000d6f000ae63c08/sensor/6ce99292-5e73-11ed-a1de-036b07b2e4f5\", \"protocol_specific\": {\"struct_version\": 20}, \"metadata_proto_spec\": {\"raw_data\": {\"dis\": \"Dimming Light\", \"unit\": \"%\", \"curVal\": \"0.0\", \"sclera_locations\": [{\"val\": \"160b7530-1187-11ed-aa44-2741238d9b18\", \"_kind\": \"ref\"}], \"sclera_attributes\": [{\"name\": \"Light Intensity\", \"unit\": \"\", \"value\": \"0.0\"}]}, \"equipment\": {\"id\": \"000d6f000ae63c08\", \"type\": \"lighting_controller\", \"unique_name\": \"000d6f000ae63c08 (375303DP231-375303DP246)\", \"sclera_point_equipment_category\": \"generic\", \"sclera_point_equipment_floor_ref\": \"8f4451cd-a0a5-11ec-bc72-3d5e4a373e02\", \"sclera_point_equipment_location_ref\": \"160b7530-1187-11ed-aa44-2741238d9b18\"}, \"raw_data_base64\": null}, \"reference_point_name\": \"Dimming Light\", \"device_object_data_type\": null}, {\"enum\": [], \"hidden\": false, \"tagged\": false, \"point_id\": \"70c8a1a9-5e73-11ed-a1de-571d6841ca53\", \"read_only\": true, \"absolute_address\": \"sclera://vmds/VDMS931/asset/000d6f000ae63c08/sensor/70c8a1a9-5e73-11ed-a1de-571d6841ca53\", \"protocol_specific\": {\"struct_version\": 20}, \"metadata_proto_spec\": {\"raw_data\": {\"dis\": \"Energy Usage\", \"unit\": \"kWh\", \"curVal\": \"0.0023508333\", \"sclera_locations\": [{\"val\": \"160b7530-1187-11ed-aa44-2741238d9b18\", \"_kind\": \"ref\"}], \"sclera_attributes\": [{\"name\": \"Energy Consumption\", \"unit\": \"\", \"value\": \"0.0023508333\"}]}, \"equipment\": {\"id\": \"000d6f000ae63c08\", \"type\": \"lighting_controller\", \"unique_name\": \"000d6f000ae63c08 (375303DP231-375303DP246)\", \"sclera_point_equipment_category\": \"generic\", \"sclera_point_equipment_floor_ref\": \"8f4451cd-a0a5-11ec-bc72-3d5e4a373e02\", \"sclera_point_equipment_location_ref\": \"160b7530-1187-11ed-aa44-2741238d9b18\"}, \"raw_data_base64\": null}, \"reference_point_name\": \"Energy Usage\", \"device_object_data_type\": null}, {\"enum\": [], \"hidden\": false, \"tagged\": false, \"point_id\": \"7453eb75-5e73-11ed-a1de-63242f0807ee\", \"read_only\": true, \"absolute_address\": \"sclera://vmds/VDMS931/asset/000d6f000ae63c08/sensor/7453eb75-5e73-11ed-a1de-63242f0807ee\", \"protocol_specific\": {\"struct_version\": 20}, \"metadata_proto_spec\": {\"raw_data\": {\"dis\": \"Configuration Status\", \"unit\": null, \"curVal\": \"Normal\", \"sclera_locations\": [{\"val\": \"160b7530-1187-11ed-aa44-2741238d9b18\", \"_kind\": \"ref\"}], \"sclera_attributes\": [{\"name\": \"Failure Since\", \"unit\": \"\", \"value\": \"0.0\"}]}, \"equipment\": {\"id\": \"000d6f000ae63c08\", \"type\": \"lighting_controller\", \"unique_name\": \"000d6f000ae63c08 (375303DP231-375303DP246)\", \"sclera_point_equipment_category\": \"generic\", \"sclera_point_equipment_floor_ref\": \"8f4451cd-a0a5-11ec-bc72-3d5e4a373e02\", \"sclera_point_equipment_location_ref\": \"160b7530-1187-11ed-aa44-2741238d9b18\"}, \"raw_data_base64\": null}, \"reference_point_name\": \"Configuration Status\", \"device_object_data_type\": null}]}");
//            System.out.println(jsonFromDataBase.get(i));
//        }
      /*  List<String> jsonFromDataBase = new ArrayList<>();
        // jsonFromDataBase.addAll(generalRepo.getJSONResponse(assetId));
        // jsonFromDataBase.add(generalRepo.getJSONResponse(assetId).toString());
        jsonFromDataBase.add("{\"datapoints\": [{\"enum\": [], \"hidden\": false, \"tagged\": false, \"point_id\": \"5f51cdea-5e73-11ed-a1de-ed2fb742e341\", \"read_only\": true, \"absolute_address\": \"sclera://vmds/VDMS931/asset/000d6f000ae63c08/sensor/5f51cdea-5e73-11ed-a1de-ed2fb742e341\", \"protocol_specific\": {\"struct_version\": 20}, \"metadata_proto_spec\": {\"raw_data\": {\"dis\": \"Communication Status\", \"unit\": null, \"curVal\": \"Normal\", \"sclera_locations\": [{\"val\": \"160b7530-1187-11ed-aa44-2741238d9b18\", \"_kind\": \"ref\"}], \"sclera_attributes\": [{\"name\": \"Failure Since\", \"unit\": \"\", \"value\": \"0.0\"}], \"measuring_instrument_type\": \"Communication Status\"}, \"equipment\": {\"id\": \"000d6f000ae63c08\", \"type\": \"lighting_controller\", \"unique_name\": \"000d6f000ae63c08 (375303DP231-375303DP246)\", \"sclera_point_equipment_category\": \"generic\", \"sclera_point_equipment_floor_ref\": \"8f4451cd-a0a5-11ec-bc72-3d5e4a373e02\", \"sclera_point_equipment_location_ref\": \"160b7530-1187-11ed-aa44-2741238d9b18\"}, \"raw_data_base64\": null}, \"reference_point_name\": \"Communication Status\", \"device_object_data_type\": null}, {\"enum\": [], \"hidden\": false, \"tagged\": false, \"point_id\": \"6ce99292-5e73-11ed-a1de-036b07b2e4f5\", \"read_only\": true, \"absolute_address\": \"sclera://vmds/VDMS931/asset/000d6f000ae63c08/sensor/6ce99292-5e73-11ed-a1de-036b07b2e4f5\", \"protocol_specific\": {\"struct_version\": 20}, \"metadata_proto_spec\": {\"raw_data\": {\"dis\": \"Dimming Light\", \"unit\": \"%\", \"curVal\": \"0.0\", \"sclera_locations\": [{\"val\": \"160b7530-1187-11ed-aa44-2741238d9b18\", \"_kind\": \"ref\"}], \"sclera_attributes\": [{\"name\": \"Light Intensity\", \"unit\": \"\", \"value\": \"0.0\"}], \"measuring_instrument_type\": \"Light Intensity\"}, \"equipment\": {\"id\": \"000d6f000ae63c08\", \"type\": \"lighting_controller\", \"unique_name\": \"000d6f000ae63c08 (375303DP231-375303DP246)\", \"sclera_point_equipment_category\": \"generic\", \"sclera_point_equipment_floor_ref\": \"8f4451cd-a0a5-11ec-bc72-3d5e4a373e02\", \"sclera_point_equipment_location_ref\": \"160b7530-1187-11ed-aa44-2741238d9b18\"}, \"raw_data_base64\": null}, \"reference_point_name\": \"Dimming Light\", \"device_object_data_type\": null}, {\"enum\": [], \"hidden\": false, \"tagged\": false, \"point_id\": \"70c8a1a9-5e73-11ed-a1de-571d6841ca53\", \"read_only\": true, \"absolute_address\": \"sclera://vmds/VDMS931/asset/000d6f000ae63c08/sensor/70c8a1a9-5e73-11ed-a1de-571d6841ca53\", \"protocol_specific\": {\"struct_version\": 20}, \"metadata_proto_spec\": {\"raw_data\": {\"dis\": \"Energy Usage\", \"unit\": \"kWh\", \"curVal\": \"0.0023508333\", \"sclera_locations\": [{\"val\": \"160b7530-1187-11ed-aa44-2741238d9b18\", \"_kind\": \"ref\"}], \"sclera_attributes\": [{\"name\": \"Energy Consumption\", \"unit\": \"\", \"value\": \"0.0023508333\"}], \"measuring_instrument_type\": \"Energy Consumption\"}, \"equipment\": {\"id\": \"000d6f000ae63c08\", \"type\": \"lighting_controller\", \"unique_name\": \"000d6f000ae63c08 (375303DP231-375303DP246)\", \"sclera_point_equipment_category\": \"generic\", \"sclera_point_equipment_floor_ref\": \"8f4451cd-a0a5-11ec-bc72-3d5e4a373e02\", \"sclera_point_equipment_location_ref\": \"160b7530-1187-11ed-aa44-2741238d9b18\"}, \"raw_data_base64\": null}, \"reference_point_name\": \"Energy Usage\", \"device_object_data_type\": null}, {\"enum\": [], \"hidden\": false, \"tagged\": false, \"point_id\": \"7453eb75-5e73-11ed-a1de-63242f0807ee\", \"read_only\": true, \"absolute_address\": \"sclera://vmds/VDMS931/asset/000d6f000ae63c08/sensor/7453eb75-5e73-11ed-a1de-63242f0807ee\", \"protocol_specific\": {\"struct_version\": 20}, \"metadata_proto_spec\": {\"raw_data\": {\"dis\": \"Configuration Status\", \"unit\": null, \"curVal\": \"Normal\", \"sclera_locations\": [{\"val\": \"160b7530-1187-11ed-aa44-2741238d9b18\", \"_kind\": \"ref\"}], \"sclera_attributes\": [{\"name\": \"Failure Since\", \"unit\": \"\", \"value\": \"0.0\"}], \"measuring_instrument_type\": \"Configuration Status\"}, \"equipment\": {\"id\": \"000d6f000ae63c08\", \"type\": \"lighting_controller\", \"unique_name\": \"000d6f000ae63c08 (375303DP231-375303DP246)\", \"sclera_point_equipment_category\": \"generic\", \"sclera_point_equipment_floor_ref\": \"8f4451cd-a0a5-11ec-bc72-3d5e4a373e02\", \"sclera_point_equipment_location_ref\": \"160b7530-1187-11ed-aa44-2741238d9b18\"}, \"raw_data_base64\": null}, \"reference_point_name\": \"Configuration Status\", \"device_object_data_type\": null}]}");
        */System.out.println("output from database");
        System.out.println(jsonFromDataBase);
        return updatedResponse(jsonFromDataBase);
    }

    public List<String> updatedResponse(List<String> listJsonInputResponse) throws IOException, JSONException {

        String[] WBMS_EQUIPMENT_TYPES = {"None", "Air Quality", "Meter", "Occupancy Sensor", "Damper Actuator", "Contact", "Valve Actuator", "Dehumidifier", "Damper", "Electrical Panel", "Circuit Breaker", "Air Curtain", "Burner", "Equip", "Tank", "Condensor", "Device", "Thermostat", "Generator", "Lift", "Dosing Unit", "Evaporator", "Compressor", "Switch", "Transformer", "Valve", "Battery", "VRF", "Weather Station", "Conduit", "Duct", "Pipe", "Wire", "Fume Hood", "Zone Sensor", "AHU", "Electricity Meter", "AC Electricity Meter", "DC Electricity Meter", "Flow Meter", "Thermal Meter", "Panel", "Controls Panel", "Rack", "Filter", "Zone", "Boiler", "Vibration sensor", "Chiller", "Fan", "Pump", "UPS", "PDU", "VESDA", "Surface Sensor", "Humidifier", "Heat Recovery", "Condenser", "Cooling Tower", "Heat Exchanger", "Heat Pump", "Luminaire", "UV", "Air Handling Equipment", "Actuator", "MAU", "RTU", "CRAC", "Unit Vent", "Air Terminal Unit", "CAV", "Cooling-only VAV", "Fan-powered VAV", "Hot Water Boiler", "Steam Boiler", "Coil", "Cooling Coil", "Heating Coil", "Motor", "Fan Motor", "Pump Motor", "VFD", "Plant", "Chilled Water Plant", "Hot Water Plant", "Steam Plant", "Radiant Equipment", "Chilled Beam", "Radiant Floor", "Radiator", "Vertical Transport", "Elevator", "Escalator", "Moving Walkway", "Calorifier", "Exhaust Fan", "Heat Wheel", "VAV", "Heat Trace", "FCU", "Pressurization Unit"};

        String[] FULL_FORM_EQUIPMENT_TYPES = {"None", "Air Quality", "Meter", "Occupancy Sensor", "Damper Actuator", "Contact", "Valve Actuator", "Dehumidifier", "Damper", "Electrical Panel", "Circuit Breaker", "Air Curtain", "Burner", "Equip", "Tank", "Condensor", "Device", "Thermostat", "Generator", "Lift", "Dosing Unit", "Evaporator", "Compressor", "Switch", "Transformer", "Valve", "Battery", "VRF-Variable Refrigerant Flow", "Weather Station", "Conduit", "Duct", "Pipe", "Wire", "Fume Hood", "Zone Sensor", "AHU-Air Handling Unit", "Electricity Meter", "AC Electricity Meter-Alternating Current Electricity Meter", "DC Electricity Meter-Direct Current Electricity Meter", "Flow Meter", "Thermal Meter", "Panel", "Controls Panel", "Rack", "Filter", "Zone", "Boiler", "Vibration sensor", "Chiller", "Fan", "Pump", "UPS-Uninterruptible Power Supply", "PDU-Power Distribution Unit", "VESDA-Very Early Smoke Detection Apparatus", "Surface Sensor", "Humidifier", "Heat Recovery", "Condenser", "Cooling Tower", "Heat Exchanger", "Heat Pump", "Luminaire", "UV-Ultraviolet", "Air Handling Equipment", "Actuator", "MAU-Makeup Air Unit", "RTU-Roof-Top Unit", "CRAC-Computer Room Air Conditioner", "Unit Vent", "Air Terminal Unit", "CAV-Constant Air Volume", "Cooling-only VAV-Cooling-only Variable Air Volume", "Fan-powered VAV-Fan-powered Variable Air Volume", "Hot Water Boiler", "Steam Boiler", "Coil", "Cooling Coil", "Heating Coil", "Motor", "Fan Motor", "Pump Motor", "VFD-Variable Frequency Drive", "Plant", "Chilled Water Plant", "Hot Water Plant", "Steam Plant", "Radiant Equipment", "Chilled Beam", "Radiant Floor", "Radiator", "Vertical Transport", "Elevator", "Escalator", "Moving Walkway", "Calorifier", "Exhaust Fan", "Heat Wheel", "VAV-Variable Air Volume", "Heat Trace", "FCU-Fan Coil Unit", "Pressurization Unit"};
        List<String> updatedJsonList = new ArrayList<>();


        for (String jsonInputResponse : listJsonInputResponse) {
            JsonObject jsonObject = new Gson().fromJson(jsonInputResponse, JsonObject.class);
            JsonArray datapointsArray = jsonObject.getAsJsonArray("datapoints");

            // Collect all the updated datapoints
            JsonArray updatedDataPoints = new JsonArray();
            for (JsonElement datapointElement : datapointsArray) {
                JsonObject datapointObject = datapointElement.getAsJsonObject();
                JsonObject metadataProtoSpec = datapointObject.getAsJsonObject("metadata_proto_spec");

                if (metadataProtoSpec != null && metadataProtoSpec.has("equipment")) {
                    JsonObject equipmentObject = metadataProtoSpec.getAsJsonObject("equipment");
                    JsonElement typeElement = equipmentObject.get("type");

                    if (!typeElement.isJsonNull()) {

                        String currentType = typeElement.getAsString();

                        System.out.println(currentType);
                        if (currentType.equals("packaged_outdoor_HVAC_equipment - Packaged Outdoor Heating, Ventilation, and Air Conditioning Equipment")) {
                            System.out.println("here");
                            equipmentObject.addProperty("type", "RTU");

                        } else {
                            currentType = currentType.replaceAll("[-_]", " ");
                            String mostSimilarType = null;
                            double highestScore = 0.0;
                            for (int i = 0; i < FULL_FORM_EQUIPMENT_TYPES.length; i++) {
                                String cleanedEquipmentType = FULL_FORM_EQUIPMENT_TYPES[i].replaceAll("[-_]", " ");

                                double score = FuzzySearch.ratio(currentType, cleanedEquipmentType);
                                if (score > highestScore) {
                                    highestScore = score;
                                    mostSimilarType = WBMS_EQUIPMENT_TYPES[i];
                                }
                            }

                            if (mostSimilarType != null) {

                                equipmentObject.addProperty("type", mostSimilarType);
                            } else {
                                equipmentObject.add("type", JsonNull.INSTANCE);
                            }
                        }
                    }
                }


                updatedDataPoints.add(datapointObject);
            }

            JsonObject updatedJsonObject = new JsonObject();
            updatedJsonObject.add("datapoints", updatedDataPoints);
            updatedJsonList.add(updatedJsonObject.toString());
        }

        return randomUUID(updatedJsonList);

    }

    public List<String> randomUUID(List<String> jsonWithoutRandomUUID) throws JSONException {
        List<String> jsonWithRandomUUID = new ArrayList<>();
        String randomUUid = UUID.randomUUID().toString();

        for (String json : jsonWithoutRandomUUID) {
            try {
                JSONObject jsonObj = new JSONObject(json);
                JSONArray datapointsArray = jsonObj.getJSONArray("datapoints");

                for (int i = 0; i < datapointsArray.length(); i++) {
                    JSONObject datapoint = datapointsArray.getJSONObject(i);
                    JSONObject metadataProtoSpec = datapoint.getJSONObject("metadata_proto_spec");
                    JSONObject equipment = metadataProtoSpec.getJSONObject("equipment");
                    String unique_name = equipment.get("unique_name").toString();
//                    System.out.println(unique_name+"fgbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
                    // Modify the 'id' of the 'equipment' object
                    equipment.put("id", randomUUid);
                    equipment.put("unique_name", unique_name + "-" + randomUUid);

                }

                // Add the modified JSON object to the list
                jsonWithRandomUUID.add(jsonObj.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return checkMarker(jsonWithRandomUUID);
    }

//    private static boolean containsCategoryValue(String[] list, String value) {
//        for (String marker : list) {
//            if (marker.trim().equals(value)) {
//                return true;
//            }
//        }
//        return false;
//    }

    public List<String> checkMarker(List<String> inputToCheckMarker) throws JSONException {
        String[] allMarkersList = {" absorption ", " chiller-absorption ", " ac ", " accumulate ", " inheritance ", " defx ", " active ", " alarm ", " angle ", " apparent ", " atmospheric ", " avg ", " barometric ", " blowdown ", " centrifugal ", " chiller-centrifugal ", " chilled ", " choice ", " ahuZoneDelivery ", " chilledBeamZone ", " directZone ", " multiZone ", " vavZone ", " airVolumeAdjustability ", " constantAirVolume ", " variableAirVolume ", " atesDesign ", " atesClosedLoop ", " atesDoublet ", " atesDoubletPaired ", " atesMono ", " atesUnidirectional ", " chillerMechanism ", " chiller-absorption ", " chiller-centrifugal ", " chiller-reciprocal ", " chiller-rotaryScrew ", " condenserLoop ", " condenserClosedLoop ", " condenserOpenLoop ", " coolingProcess ", " airCooling ", " chilledWaterCooling ", " condenserCooling ", " dxCooling ", " waterCooling ", " ductConfig ", " dualDuct ", " singleDuct ", " tripleDuct ", " ductDeck ", " coldDeck ", " hotDeck ", " neutralDeck ", " ductSection ", " discharge ", " economizer ", " exhaust ", " flue ", " inlet ", " mixed ", " outside ", " return ", " ventilation ", " heatingProcess ", " biomassHeating ", " coalHeating ", " dxHeating ", " elecHeating ", " fuelOilHeating ", " hotWaterHeating ", " naturalGasHeating ", " propaneHeating ", " steamHeating ", " meterScope ", " siteMeter ", " submeter ", " phaseCount ", " singlePhase ", " threePhase ", " pipeFluid ", " pipeSection ", " bypass ", " circ ", " entering ", " header ", " leaving ", " plantLoop ", " primaryLoop ", " secondaryLoop ", " tertiaryLoop ", " pointFunction ", " cmd ", " sensor ", " sp ", " pointQuantity ", " pointSubject ", " tankSubstance ", " vavAirCircuit ", " vav-parallel ", " vav-series ", " vavModulation ", " pressureDependent ", " pressureIndependent ", " co2e ", " cold ", " compressor ", " computed ", " condenser ", " conditioning ", " controls ", " cool ", " cooling ", " coolingOnly ", " cur ", " current ", " damper ", " dc ", " deadband ", " delta ", " demand ", " deprecated ", " design ", " dessicantDehumidifier ", " diverting ", " docAssociations ", " docTaxonomy ", " domestic ", " ductArea ", " vav ", " economizing ", " effective ", " efficiency ", " enable ", " point ", " equip ", " vfd ", " entity ", " device ", " computer ", " laptop ", " server-computer ", " controller ", " networking-device ", " networking-router ", " networking-switch ", " phone ", " mobile-phone ", " tablet ", " equip ", " actuator ", " damper-actuator ", " valve-actuator ", " airHandlingEquip ", " ahu ", " doas ", " mau ", " rtu ", " fcu ", " crac ", " unitVent ", " vrf-indoorUnit-fcu ", " airTerminalUnit ", " cav ", " vav ", " assembly ", " ates ", " battery ", " boiler ", " hot-water-boiler ", " steam-boiler ", " cable ", " chiller ", " circuit ", " conduit ", " duct ", " pipe ", " wire ", " coolingTower ", " evse-equip ", " evse-assembly ", " evse-cable ", " evse-port ", " evse-port ", " flowInverter ", " fumeHood ", " heatExchanger ", " coil ", " coolingCoil ", " heatingCoil ", " humidifier-equip ", " luminaire ", " meter ", " elec-meter ", " ac-elec-meter ", " dc-elec-meter ", " evse-port ", " flow-meter ", " motor ", " fan-motor ", " pump-motor ", " panel ", " controls-panel ", " elec-panel ", " plant ", " chilled-water-plant ", " hot-water-plant ", " steam-plant ", " vrf-refrig-plant ", " rack ", " radiantEquip ", " chilledBeam ", " radiantFloor ", " radiator ", " tank ", " thermostat ", " ups ", " verticalTransport ", " elevator ", " escalator ", " movingWalkway ", " vrf-equip ", " branchSelector ", " vrf-indoorUnit ", " vrf-indoorUnit-fcu ", " vrf-outdoorUnit ", " well ", " network ", " point ", " cur-point ", " his-point ", " weather-point ", " writable-point ", " pointGroup ", " airQualityZonePoints ", " hvac-zone-space ", " hvacZonePoints ", " hvac-zone-space ", " thermostat ", " lightingZonePoints ", " lighting-zone-space ", " luminaire ", " site ", " space ", " dataCenter ", " floor ", " ground-floor ", " roof-floor ", " subterranean-floor ", " room ", " zone-space ", " hvac-zone-space ", " lighting-zone-space ", " system ", " air-system ", " air-conditioning-system ", " air-exhaust-system ", " air-ventilation-system ", " elec-system ", " evse-system ", " lighting-system ", " refrig-system ", " vrf-system ", " vrf-coolingOnly-system ", " vrf-heatPump-system ", " vrf-heatRecovery-system ", " water-system ", " chilled-water-system ", " condenser-water-system ", " domestic-water-system ", " domestic-cold-water-system ", " domestic-hot-water-system ", " hot-water-system ", " steam-system ", " weatherStation ", " evaporator ", " evse ", " export ", " extraction ", " faceBypass ", " fan ", " fanPowered ", " filter ", " flux ", " freezeStat ", " geoPlace ", " site ", " weatherStation ", " ground ", " heat ", " heatPump ", " heatRecovery ", " heatWheel ", " heating ", " heatingOnly ", " his ", " hisTotalized ", " hot ", " humidifier ", " hvac ", " hvacMode ", " imbalance ", " import ", " indoorUnit ", " infiltration ", " input ", " air-input ", " airCooling ", " airTerminalUnit ", " cav ", " vav ", " hvac-zone-space ", " blowdown-water-input ", " chilled-water-input ", " chilledWaterCooling ", " condensate-input ", " condenser-water-input ", " domestic-water-input ", " elec-input ", " actuator ", " damper-actuator ", " valve-actuator ", " airHandlingEquip ", " ahu ", " doas ", " mau ", " rtu ", " fcu ", " crac ", " unitVent ", " vrf-indoorUnit-fcu ", " airTerminalUnit ", " cav ", " vav ", " boiler ", " hot-water-boiler ", " steam-boiler ", " chiller ", " circuit ", " coolingTower ", " elec-meter ", " ac-elec-meter ", " dc-elec-meter ", " evse-port ", " elec-panel ", " elecHeating ", " elevator ", " escalator ", " evse-equip ", " evse-assembly ", " evse-cable ", " evse-port ", " evse-port ", " fumeHood ", " luminaire ", " motor ", " fan-motor ", " pump-motor ", " movingWalkway ", " fuelOil-input ", " fuelOilHeating ", " gasoline-input ", " hot-water-input ", " hotWaterHeating ", " makeup-water-input ", " naturalGas-input ", " naturalGasHeating ", " refrig-input ", " branchSelector ", " vrf-indoorUnit ", " vrf-indoorUnit-fcu ", " steam-input ", " steamHeating ", " intensity ", " isolation ", " lighting ", " load ", " luminous ", " magnitude ", " makeup ", " mandatory ", " max ", " mech ", " min ", " mixing ", " mobile ", " net ", " networking ", " noSideEffects ", " nodoc ", " nosrc ", " notInherited ", " occ ", " occupancy ", " occupants ", " occupied ", " openEnum ", " outdoorUnit ", " output ", " air-output ", " airHandlingEquip ", " ahu ", " doas ", " mau ", " rtu ", " fcu ", " crac ", " unitVent ", " vrf-indoorUnit-fcu ", " airTerminalUnit ", " cav ", " vav ", " blowdown-water-output ", " chilled-water-output ", " chilled-water-plant ", " condensate-output ", " condenser-water-output ", " domestic-water-output ", " elec-output ", " battery ", " circuit ", " elec-meter ", " ac-elec-meter ", " dc-elec-meter ", " evse-port ", " elec-panel ", " evse-equip ", " evse-assembly ", " evse-cable ", " evse-port ", " evse-port ", " fuelOil-output ", " gasoline-output ", " hot-water-output ", " hot-water-boiler ", " hot-water-plant ", " makeup-water-output ", " naturalGas-output ", " refrig-output ", " vrf-outdoorUnit ", " vrf-refrig-plant ", " steam-output ", " steam-boiler ", " steam-plant ", " parallel ", " perimeterHeat ", " vav ", " phenomenon ", " elec ", " ac-elec ", " dc-elec ", " light ", " solar ", " substance ", " biomass ", " fluid ", " gas ", " air ", " ch2o ", " ch4 ", " co ", " co2 ", " hfc ", " n2o ", " naturalGas ", " nf3 ", " nh3 ", " no2 ", " o2 ", " o3 ", " pfc ", " propane ", " sf6 ", " steam ", " tvoc ", " liquid ", " condensate ", " diesel ", " fuelOil ", " gasoline ", " water ", " blowdown-water ", " chilled-water ", " cold-water ", " condenser-water ", " cool-water ", " domestic-water ", " ground-water ", " hot-water ", " makeup-water ", " purge-water ", " warm-water ", " refrig ", " solid ", " coal ", " ice ", " pm01 ", " pm10 ", " pm25 ", " weather ", " wind ", " port ", " process ", " protocol ", " bacnet ", " bluetooth ", " coap ", " dali ", " ftp ", " haystack ", " http ", " imap ", " knx ", " modbus ", " mqtt ", " obix ", " pop3 ", " smtp ", " snmp ", " sox ", " zigbee ", " zwave ", " pump ", " purge ", " quality ", " quantity ", " air-velocity ", " apparent-energy ", " apparent-power ", " apparent-power ", " cloudage ", " concentration ", " airQuality ", " ch2o-concentration ", " co-concentration ", " co2-concentration ", " nh3-concentration ", " no2-concentration ", " o3-concentration ", " pm01-concentration ", " pm10-concentration ", " pm25-concentration ", " tvoc-concentration ", " current-angle ", " current-imbalance ", " current-thd ", " daytime ", " dewPoint ", " direction ", " wind-direction ", " elec-current ", " current-magnitude ", " elec-volt ", " volt-magnitude ", " emission ", " ch4-emission ", " co2-emission ", " hfc-emission ", " n2o-emission ", " nf3-emission ", " pfc-emission ", " sf6-emission ", " energy ", " elec-energy ", " active-energy ", " active-power ", " enthalpy ", " feelsLike ", " flow ", " freq ", " ac-freq ", " humidity ", " illuminance ", " irradiance ", " solar-irradiance ", " level ", " light-level ", " luminance ", " luminous-flux ", " luminous-intensity ", " pf ", " power ", " elec-power ", " active-power ", " elec-demand ", " precipitation ", " pressure ", " atmospheric-pressure ", " reactive-energy ", " reactive-power ", " reactive-power ", " speed ", " wind-speed ", " temp ", " air-temp ", " vfd-freq ", " vfd-speed ", " visibility ", " volt-angle ", " volt-imbalance ", " volume ", " weatherCond ", " wetBulb ", " rated ", " reactive ", " reciprocal ", " chiller-reciprocal ", " reheat ", " roof ", " rotaryScrew ", " chiller-rotaryScrew ", " router ", " run ", " point ", " equip ", " vfd ", " series ", " server ", " serviceFactor ", " standby ", " subterranean ", " switch ", " thd ", " thermal ", " total ", " transient ", " transitive ", " relationship ", " unocc ", " valve ", " velocity ", " vfd ", " volt ", " vrf ", " warm ", " writable ", " zone "};
        List<String> updatedJSONs = new ArrayList<>();

        for (String jsonString : inputToCheckMarker) {
            try {
                char firstChar = jsonString.trim().charAt(0);

                if (firstChar == '[') {
                    JSONArray jsonArray = new JSONArray(jsonString);
                    JSONArray updatedDataPointsArray = new JSONArray();

                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(j);
                        JSONArray dataPointsArray = jsonObject.getJSONArray("datapoints");

                        JSONArray updatedDataPoints = new JSONArray();

                        for (int i = 0; i < dataPointsArray.length(); i++) {
                            JSONObject dataPoint = dataPointsArray.getJSONObject(i);
                            updateDataPoint(dataPoint, allMarkersList);
                            updatedDataPoints.put(dataPoint);
                        }

                        JSONObject updatedJsonObject = new JSONObject();
                        updatedJsonObject.put("datapoints", updatedDataPoints);

                        updatedDataPointsArray.put(updatedJsonObject);
                    }

                    JSONObject updatedJsonObj = new JSONObject();
                    updatedJsonObj.put("datapoints", updatedDataPointsArray);

                    updatedJSONs.add(updatedJsonObj.toString());
                } else if (firstChar == '{') {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray dataPointsArray = jsonObject.getJSONArray("datapoints");

                    JSONArray updatedDataPoints = new JSONArray();

                    for (int i = 0; i < dataPointsArray.length(); i++) {
                        JSONObject dataPoint = dataPointsArray.getJSONObject(i);
                        updateDataPoint(dataPoint, allMarkersList);
                        updatedDataPoints.put(dataPoint);
                    }

                    JSONObject updatedJsonObject = new JSONObject();
                    updatedJsonObject.put("datapoints", updatedDataPoints);

                    updatedJSONs.add(updatedJsonObject.toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        System.out.println("final output:" + updatedJSONs);
        return updatedJSONs;
    }
}

