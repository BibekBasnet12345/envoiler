package com.example.Envoiler.Repo;

import com.example.Envoiler.entity.General;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GeneralRepo extends JpaRepository<General,Long> {
    @Query(nativeQuery = true,value = "with \n" +
            "const as (select :assetId as d_id),\n" +
            "dev_mes as (select d.id device_id, d.user_data_name ,d.display_name,d.type device_type, d.category category,d.docker_vdms_id vdmsid,m.id point_id, m.name point_name,m.type measuring_instrument_type, m.value curval, m.unit unit\n" +
            "from const c,device d join measuring_instrument m on d.id = m.device_id where d.id = c.d_id),\n" +
            "dev_loc as (select d.id dev_id,d.location_id, l.floor_id \n" +
            "from const c, device d join location l on l.id = d.location_id where c.d_id = d.id),\n" +
            "meas_loc as (select mil.measuring_instrument_id measurement_id,mil.location_id from measuring_instrument_location mil join measuring_instrument mi on mi.id =mil.measuring_instrument_id),\n" +
            "att_extract AS (\n" +
            "SELECT\n" +
            "\tmi.id meas_id,\n" +
            "\tmi.device_id device_id,\n" +
            "\tCASE\n" +
            "\t\tWHEN JSON_UNQUOTE(JSON_EXTRACT(attribute , '$.parameter_1_name')) IS NOT NULL and JSON_UNQUOTE(JSON_EXTRACT(attribute , '$.parameter_1_type')) != \"manual\" THEN\n" +
            "    \t\t\tJSON_OBJECT(\n" +
            "\t\t            'name', COALESCE(JSON_UNQUOTE(JSON_EXTRACT(attribute , '$.parameter_1_name')),\"\"),\n" +
            "\t\t            'value', COALESCE(JSON_UNQUOTE(JSON_EXTRACT(attribute , '$.parameter_1_value')),\"\"),\n" +
            "\t\t            'unit', COALESCE(JSON_UNQUOTE(JSON_EXTRACT(attribute , '$.parameter_1_unit')),\"\")\n" +
            "\t\t        )\n" +
            "\t\tELSE NULL\n" +
            "\tEND as parameter_1,\n" +
            "\tCASE\n" +
            "\t\tWHEN JSON_UNQUOTE(JSON_EXTRACT(attribute , '$.parameter_2_name')) IS NOT NULL and JSON_UNQUOTE(JSON_EXTRACT(attribute , '$.parameter_2_type')) != \"manual\" THEN\n" +
            "    \t\t\tJSON_OBJECT(\n" +
            "\t\t            'name', COALESCE(JSON_UNQUOTE(JSON_EXTRACT(attribute , '$.parameter_2_name')),\"\"),\n" +
            "\t\t            'value', COALESCE(JSON_UNQUOTE(JSON_EXTRACT(attribute , '$.parameter_2_value')),\"\"),\n" +
            "\t\t            'unit', COALESCE(JSON_UNQUOTE(JSON_EXTRACT(attribute , '$.parameter_2_unit')),\"\")\n" +
            "\t\t        )\n" +
            "\t\tELSE NULL\n" +
            "\tEND as parameter_2,\n" +
            "\tCASE\n" +
            "\t\tWHEN JSON_UNQUOTE(JSON_EXTRACT(attribute , '$.parameter_3_name')) IS NOT NULL and JSON_UNQUOTE(JSON_EXTRACT(attribute , '$.parameter_3_type')) != \"manual\" THEN\n" +
            "    \t\t\tJSON_OBJECT(\n" +
            "\t\t            'name', COALESCE(JSON_UNQUOTE(JSON_EXTRACT(attribute , '$.parameter_3_name')),\"\"),\n" +
            "\t\t            'value', COALESCE(JSON_UNQUOTE(JSON_EXTRACT(attribute , '$.parameter_3_value')),\"\"),\n" +
            "\t\t            'unit', COALESCE(JSON_UNQUOTE(JSON_EXTRACT(attribute , '$.parameter_3_unit')),\"\")\n" +
            "\t\t        )\n" +
            "\t\tELSE NULL\n" +
            "\tEND as parameter_3,\n" +
            "\tCASE\n" +
            "\t\tWHEN JSON_UNQUOTE(JSON_EXTRACT(attribute , '$.parameter_4_name')) IS NOT NULL and JSON_UNQUOTE(JSON_EXTRACT(attribute , '$.parameter_4_type')) != \"manual\" THEN\n" +
            "    \t\t\tJSON_OBJECT(\n" +
            "\t\t            'name', COALESCE(JSON_UNQUOTE(JSON_EXTRACT(attribute , '$.parameter_4_name')),\"\"),\n" +
            "\t\t            'value', COALESCE(JSON_UNQUOTE(JSON_EXTRACT(attribute , '$.parameter_4_value')),\"\"),\n" +
            "\t\t            'unit', COALESCE(JSON_UNQUOTE(JSON_EXTRACT(attribute , '$.parameter_4_unit')),\"\")\n" +
            "\t\t        )\n" +
            "\t\tELSE NULL\n" +
            "\tEND as parameter_4,\n" +
            "\tCASE\n" +
            "\t\tWHEN JSON_UNQUOTE(JSON_EXTRACT(attribute , '$.parameter_5_name')) IS NOT NULL and JSON_UNQUOTE(JSON_EXTRACT(attribute , '$.parameter_5_type')) != \"manual\" THEN\n" +
            "    \t\t\tJSON_OBJECT(\n" +
            "\t\t            'name', COALESCE(JSON_UNQUOTE(JSON_EXTRACT(attribute , '$.parameter_5_name')),\"\"),\n" +
            "\t\t            'value', COALESCE(JSON_UNQUOTE(JSON_EXTRACT(attribute , '$.parameter_5_value')),\"\"),\n" +
            "\t\t            'unit', COALESCE(JSON_UNQUOTE(JSON_EXTRACT(attribute , '$.parameter_5_unit')),\"\")\n" +
            "\t\t        )\n" +
            "\t\tELSE NULL\n" +
            "\tEND as parameter_5\n" +
            "FROM\n" +
            "\tmeasuring_instrument mi\n" +
            "),\n" +
            "att_array as (\n" +
            "SELECT\n" +
            "\ta.device_id,\n" +
            "\ta.meas_id,\n" +
            "\treplace(replace(replace(replace(\n" +
            "\t\t\tJSON_ARRAY(\n" +
            "\t\t    parameter_1,\n" +
            "\t\t    parameter_2,\n" +
            "\t\t    parameter_3,\n" +
            "\t\t    parameter_4,\n" +
            "\t\t    parameter_5\n" +
            "\t\t)\n" +
            "\t, \", null\" , ''),\"null,\",''), \"null\" , ''),'\"<>empty<>\"','null')  \n" +
            "\tas attributes\n" +
            "FROM\n" +
            "\tatt_extract a, const c where c.d_id = a.device_id\n" +
            ")\n" +
            "select json_object(\n" +
            "\"datapoints\",\n" +
            "JSON_ARRAYAGG(\n" +
            "JSON_OBJECT(\n" +
            "\"device_object_data_type\",\n" +
            "null,\n" +
            "\"point_id\",d.point_id,\"read_only\",TRUE ,\"enum\",JSON_ARRAY() ,\"hidden\",FALSE ,'tagged',FALSE ,'reference_point_name',d.point_name,\n" +
            "'protocol_specific',JSON_OBJECT(\"struct_version\",20),\n" +
            "'absolute_address',concat(\"sclera://vmds/\",d.vdmsid,\"/asset/\",d.device_id,\"/sensor/\",d.point_id),\n" +
            "\"metadata_proto_spec\",\n" +
            "JSON_OBJECT(\"equipment\",\n" +
            "JSON_OBJECT(\"id\",d.device_id,\"unique_name\",COALESCE(d.user_data_name,d.display_name),\"type\",d.device_type,\n" +
            "\"sclera_point_equipment_location_ref\",l.location_id,\"sclera_point_equipment_floor_ref\",l.floor_id,\"sclera_point_equipment_category\",d.category),\n" +
            "\"raw_data_base64\",null,\"raw_data\", \n" +
            "JSON_OBJECT(\"dis\",d.point_name,\"curVal\",d.curval,\"unit\",d.unit, \"measuring_instrument_type\", d.measuring_instrument_type,\n" +
            "\"sclera_attributes\", cast(aa.attributes as json), \"sclera_locations\",\n" +
            "        CASE\n" +
            "\t        WHEN ml.location_id IS NOT NULL AND l.location_id IS NOT NULL THEN\n" +
            "            JSON_ARRAY( JSON_OBJECT(\"_kind\", \"ref\", \"val\", ml.location_id),JSON_OBJECT(\"_kind\", \"ref\", \"val\", l.location_id))\n" +
            "            WHEN ml.location_id IS NOT NULL THEN JSON_ARRAY( json_object(\"_kind\",\"ref\",\"val\",ml.location_id))\n" +
            "            when l.location_id IS NOT NULL THEN JSON_ARRAY( json_object(\"_kind\",\"ref\",\"val\",l.location_id))\n" +
            "            ELSE null\n" +
            "        END   \n" +
            ") )) ))\n" +
            "from dev_mes d \n" +
            "left join dev_loc l on l.dev_id = d.device_id\n" +
            "left join att_array aa on aa.meas_id = d.point_id \n" +
            "left join meas_loc ml on ml.measurement_id = d.point_id;")
    List<String> getJSONResponse(@Param("assetId") String assetId);

}
