package org.dvsa.testing.framework;

import activesupport.system.out.Output;
import org.dvsa.testing.framework.exception.DefinedDRActionTypeException;
import org.dvsa.testing.framework.exception.UndefinedDRActionTypeException;
import org.jetbrains.annotations.NotNull;

public enum DRQuery {
    IRFO_OPERATOR_EXPIRED(
            " SELECT id, " +
                    "          retention_period, " +
                    "          max_data_set, " +
                    "          action_type " +
                    "   INTO @v_rule_id, " +
                    "        @v_retention_period, " +
                    "        @v_max_data_set, " +
                    "        @v_action_type " +
                    "   FROM data_retention_rule " +
                    "   WHERE populate_procedure='sp_populate_irfo_operator_expired'; " +
                    " SET @query = CONCAT(\"SELECT  " +
                    "    'organisation' AS 'entity_name', " +
                    "    o.id AS 'entity_pk', " +
                    "    name AS 'organisation_name', " +
                    "    o.id AS 'organisation_id', " +
                    "    @v_rule_id AS 'data_retention_rule_id', " +
                    "    CASE@v_action_type " +
                    "        WHEN 'Automate' THEN TRUE " +
                    "        ELSE FALSE " +
                    "    END AS 'action_confirmation', " +
                    "    '1' AS 'created_by', " +
                    "    SYSDATE() AS 'created_on' " +
                    "FROM " +
                    "    OLCS_RDS_OLCSDB.organisation o " +
                    "WHERE " +
                    "    type = 'org_t_ir' " +
                    "        AND DATE(o.created_on) < DATE(DATE_SUB(NOW(), " +
                    "            INTERVAL @v_retention_period MONTH)) " +
                    "        AND o.id NOT IN (SELECT  " +
                    "            organisation_id " +
                    "        FROM " +
                    "            OLCS_RDS_OLCSDB.irfo_psv_auth ipa " +
                    "        WHERE " +
                    "            organisation_id = o.id) " +
                    "        AND o.id NOT IN (SELECT  " +
                    "            organisation_id " +
                    "        FROM " +
                    "            OLCS_RDS_OLCSDB.irfo_gv_permit " +
                    "        WHERE " +
                    "            organisation_id = o.id) " +
                    "        AND o.id NOT IN (SELECT  " +
                    "            organisation_id " +
                    "        FROM " +
                    "            OLCS_RDS_OLCSDB.licence " +
                    "        WHERE " +
                    "            organisation_id = o.id) " +
                    "        AND o.id NOT IN (SELECT  " +
                    "            entity_pk " +
                    "        FROM " +
                    "            OLCS_RDS_OLCSDB.data_retention " +
                    "        WHERE " +
                    "            entity_pk = o.id " +
                    "                AND entity_name = 'organisation') " +
                    "LIMIT \",@v_max_data_set); " +
                    " " +
                    "PREPARE stmt1 FROM @query;  " +
                    "EXECUTE stmt1;  " +
                    "DEALLOCATE PREPARE stmt1; "
    ),
    IRFO_GV_PERMIT_EXPIRED(
            " SELECT id, " +
                    "           retention_period, " +
                    "           max_data_set, " +
                    "           action_type " +
                    "    INTO @v_rule_id, " +
                    "         @v_retention_period, " +
                    "         @v_max_data_set, " +
                    "         @v_action_type " +
                    "    FROM data_retention_rule " +
                    "    WHERE populate_procedure='sp_populate_irfo_gv_permit_expired'; " +
                    " " +
                    "    SET @query = CONCAT(\"SELECT 'irfo_gv_permit' AS 'entity_name', " +
                    "           i.id AS 'entity_pk', " +
                    "           o.name AS 'organisation_name', " +
                    "           o.id AS 'organisation_id', " +
                    "           @v_rule_id AS 'data_retention_rule_id', " +
                    "           CASE @v_action_type WHEN 'Automate' THEN TRUE ELSE FALSE END AS action_type, " +
                    "           1 AS 'created_by', " +
                    "           sysdate() AS 'created_on' " +
                    "    FROM irfo_psv_auth i " +
                    "    JOIN organisation o ON o.id = i.organisation_id " +
                    "    WHERE DATE(expiry_date) < DATE(DATE_SUB(sysdate(),INTERVAL @v_retention_period MONTH)) " +
                    "    AND i.id NOT IN (SELECT entity_pk " +
                    "           FROM data_retention " +
                    "                   WHERE entity_pk = i.id " +
                    "                   AND entity_name = 'irfo_gv_permit') " +
                    "    LIMIT \",@v_max_data_set); " +
                    "     " +
                    "    PREPARE stmt1 FROM @query;  " +
                    "    EXECUTE stmt1;  " +
                    "    DEALLOCATE PREPARE stmt1; "
    ),
    IRFO_PSV_AUTHORISATION_EXPIRED(
            " SELECT id, " +
                    "           retention_period, " +
                    "           max_data_set, " +
                    "           action_type " +
                    "    INTO @v_rule_id, " +
                    "         @v_retention_period, " +
                    "         @v_max_data_set, " +
                    "         @v_action_type " +
                    "    FROM data_retention_rule " +
                    "    WHERE populate_procedure='sp_populate_irfo_psv_auth_expired'; " +
                    " " +
                    "    SET @query = CONCAT(\"SELECT 'irfo_psv_auth' AS 'entity_name', " +
                    "           i.id AS 'entity_pk', " +
                    "           o.name AS 'organisation_name', " +
                    "           o.id AS 'organisation_id', " +
                    "           @v_rule_id AS 'data_retention_rule_id', " +
                    "           CASE @v_action_type WHEN 'Automate' THEN TRUE ELSE FALSE END AS action_type, " +
                    "           1 AS 'created_by', " +
                    "           sysdate() AS 'created_on' " +
                    "    FROM irfo_psv_auth i " +
                    "    JOIN organisation o ON o.id = i.organisation_id " +
                    "    WHERE DATE(expiry_date) < DATE(DATE_SUB(sysdate(),INTERVAL @v_retention_period MONTH)) " +
                    "    AND i.id NOT IN (SELECT entity_pk " +
                    "           FROM data_retention " +
                    "                   WHERE entity_pk = i.id " +
                    "                   AND entity_name = 'irfo_psv_auth') " +
                    "    LIMIT \",@v_max_data_set); " +
                    "     " +
                    "    PREPARE stmt1 FROM @query;  " +
                    "    EXECUTE stmt1;  " +
                    "    DEALLOCATE PREPARE stmt1; "
    ),
    IRFO_GV_PERMIT_WITHDRAWN_PENDING_OR_REFUSED_EXPIRED(
            "SELECT id, " +
                    "          retention_period, " +
                    "          max_data_set, " +
                    "          action_type " +
                    "   INTO @v_rule_id, " +
                    "        @v_retention_period, " +
                    "        @v_max_data_set, " +
                    "        @v_action_type " +
                    "   FROM data_retention_rule " +
                    "   WHERE populate_procedure='sp_populate_irfo_gv_permit_withdrawn_pending_refused_expired'; " +
                    " " +
                    "SET @query = CONCAT(\"SELECT 'irfo_gv_permit' AS 'entity_name', " +
                    "           i.id AS 'entity_pk', " +
                    "           o.name AS 'organisation_name', " +
                    "           o.id AS 'organisation_id', " +
                    "           @v_rule_id AS 'data_retention_rule_id', " +
                    "           CASE @v_action_type WHEN 'Automate' THEN TRUE ELSE FALSE END " +
                    "            AS 'action_confirmation', " +
                    "          1 AS 'created_by', " +
                    "    SYSDATE() AS 'created_on' " +
                    "    FROM irfo_gv_permit i " +
                    "    JOIN organisation o ON o.id = i.organisation_id " +
                    "    WHERE expiry_date IS NULL " +
                    "    AND irfo_permit_status IN ('irfo_auth_s_withdrawn', 'irfo_perm_s_pending','irfo_perm_s_refused') " +
                    "    AND DATE(i.created_on) < DATE(DATE_SUB(sysdate(),INTERVAL @v_retention_period MONTH)) " +
                    "    AND i.id NOT IN (SELECT entity_pk " +
                    "       FROM data_retention " +
                    "                   WHERE entity_pk = i.id " +
                    "                   AND entity_name = 'irfo_gv_permit') " +
                    " LIMIT \",@v_max_data_set); " +
                    "  " +
                    "PREPARE stmt1 FROM @query;  " +
                    "EXECUTE stmt1;  " +
                    "DEALLOCATE PREPARE stmt1;"
    ),
    IRFO_PSV_PERMIT_WITHDRAWN_PENDING_OR_REFUSED_EXPIRED(
            " SELECT id, " +
                    "          retention_period, " +
                    "          max_data_set, " +
                    "          action_type " +
                    "   INTO @v_rule_id, " +
                    "        @v_retention_period, " +
                    "        @v_max_data_set, " +
                    "        @v_action_type " +
                    "   FROM data_retention_rule " +
                    "   WHERE populate_procedure='sp_populate_irfo_psv_auth_withdrawn_pending_refused_expired'; " +
                    "  " +
                    " SET @query = CONCAT(\"SELECT 'irfo_psv_auth' AS 'entity_name', " +
                    "           i.id AS 'entity_pk', " +
                    "           o.name  AS 'organisation_name', " +
                    "           o.id AS 'organisation_id', " +
                    "           @v_rule_id AS 'data_retention_rule_id', " +
                    "           CASE @v_action_type WHEN 'Automate' THEN TRUE ELSE FALSE END AS action_type, " +
                    "           1 AS 'created_by', " +
                    "             sysdate() AS 'created_on' " +
                    "    FROM irfo_psv_auth i " +
                    "    JOIN organisation o ON o.id = i.organisation_id " +
                    "    WHERE expiry_date IS NULL " +
                    "    AND status IN ('irfo_auth_s_withdrawn','irfo_auth_s_pending', 'irfo_auth_s_refused' ,'irfo_auth_s_granted', 'irfo_auth_s_cns') " +
                    "    AND DATE(i.created_on) < DATE(DATE_SUB(sysdate(),INTERVAL @v_retention_period MONTH)) " +
                    "    AND i.id NOT IN (SELECT entity_pk " +
                    "\t\t   FROM data_retention " +
                    "                   WHERE entity_pk = i.id " +
                    "                   AND entity_name = 'irfo_psv_auth') " +
                    "    LIMIT \",@v_max_data_set); " +
                    "    " +
                    "   PREPARE stmt1 FROM @query;  " +
                    "   EXECUTE stmt1;  " +
                    "   DEALLOCATE PREPARE stmt1;"
    ),
    LICENCE_NOT_YET_SUBMITTED(
            "SELECT id, " +
                    "          retention_period, " +
                    "          max_data_set, " +
                    "          action_type " +
                    "   INTO @v_rule_id, " +
                    "        @v_retention_period, " +
                    "        @v_max_data_set, " +
                    "        @v_action_type " +
                    "   FROM data_retention_rule " +
                    "   WHERE populate_procedure='sp_populate_licence_not_submitted'; " +
                    " " +
                    "SET @query = CONCAT(\"SELECT  " +
                    "    'licence' AS 'entity_name', " +
                    "    l.id AS 'entity_pk', " +
                    "    o.name AS 'organisation_name', " +
                    "    o.id AS 'organisation_id', " +
                    "    l.lic_no AS 'lic_no', " +
                    "    @v_rule_id AS 'data_retention_rule_id', " +
                    "    CASE @v_action_type " +
                    "        WHEN 'Automate' THEN TRUE " +
                    "        ELSE FALSE " +
                    "    END AS 'action_confirmation', " +
                    "    1 AS 'created_by', " +
                    "    SYSDATE() AS 'created_on' " +
                    "FROM " +
                    "    licence l " +
                    "        JOIN " +
                    "    organisation o ON o.id = l.organisation_id " +
                    "WHERE " +
                    "    l.id NOT IN (SELECT  " +
                    "            entity_pk " +
                    "        FROM " +
                    "            data_retention " +
                    "        WHERE " +
                    "            entity_pk = l.id " +
                    "                AND entity_name = 'licence') " +
                    "        AND status = 'lsts_not_submitted' " +
                    "        AND GETLICENCEEXPIRYDATE(l.id) < DATE(DATE_SUB(SYSDATE(), " +
                    "            INTERVAL @v_retention_period MONTH)) " +
                    "LIMIT \" ,@v_max_data_set); " +
                    " " +
                    "   PREPARE stmt1 FROM @query;  " +
                    "   EXECUTE stmt1;  " +
                    "   DEALLOCATE PREPARE stmt1;"
    ),
    APPLICATIONS_REFUSED(
            "SELECT id, " +
                    "          retention_period, " +
                    "          max_data_set, " +
                    "          action_type " +
                    "   INTO @v_rule_id, " +
                    "        @v_retention_period, " +
                    "        @v_max_data_set, " +
                    "        @v_action_type " +
                    "   FROM data_retention_rule " +
                    "   WHERE populate_procedure='sp_populate_application_refused'; " +
                    "     " +
                    "    SET @query = CONCAT(\"SELECT 'application' AS 'entity_name', " +
                    "           a.id AS 'entity_pk', " +
                    "           o.name AS 'organisation_name', " +
                    "           o.id AS 'organisation_id', " +
                    "           l.lic_no AS 'lic_no', " +
                    "           @v_rule_id AS 'data_retention_rule_id', " +
                    "           CASE @v_action_type WHEN 'Automate' THEN TRUE ELSE FALSE END AS 'action_confirmation', " +
                    "           1 AS 'created_by', " +
                    "           sysdate() AS 'created_on' " +
                    "    FROM OLCS_RDS_OLCSDB.application a " +
                    "    JOIN OLCS_RDS_OLCSDB.licence l ON l.id = a.licence_id " +
                    "    JOIN OLCS_RDS_OLCSDB.organisation o ON o.id = l.organisation_id " +
                    "    WHERE DATE(refused_date) < DATE(DATE_SUB(sysdate(),INTERVAL @v_retention_period MONTH)) " +
                    "    AND a.status = 'apsts_refused' " +
                    "    AND a.id NOT IN (SELECT entity_pk " +
                    "                     FROM OLCS_RDS_OLCSDB.data_retention " +
                    "                     WHERE entity_pk = a.id " +
                    "                     AND entity_name = 'application') " +
                    "LIMIT \",@v_max_data_set); " +
                    " " +
                    "PREPARE stmt1 FROM @query;  " +
                    "   EXECUTE stmt1;  " +
                    "   DEALLOCATE PREPARE stmt1; "
    ),
    POPULATE(
            "SELECT entity_name, " +
                    "entity_pk,organisation_name, " +
                    "organisation_id,lic_no, " +
                    "data_retention_rule_id, " +
                    "action_confirmation,created_by " +
                    "FROM OLCS_RDS_OLCSDB.data_retention " +
                    "WHERE data_retention_rule_id = %s;"
    );

    private String query;

    DRQuery(@NotNull String query) {
        this.query = query;
    }

    // TODO: Look at other alternatives that does not interpolate values into the query as it breaks down should the various queries take different arguments/ have parameters whose positions vary from query to query.
    public String getQuery(@NotNull String ruleID) throws UndefinedDRActionTypeException {
        if (this != POPULATE) {
            throw new UndefinedDRActionTypeException(String.format("Enum %s requires an Action Type to be defined, use the overloaded version of DRQuery#getQuery that takes DRActionType as an argument.", this));
        }

        return String.format(query, ruleID);
    }

    public String getQuery(@NotNull DRActionType actionType) throws DefinedDRActionTypeException {
        if (this == POPULATE) {
            throw new DefinedDRActionTypeException("Enum POPULATE should not have an DRActionType defined.");
        }

        return String.format(query, actionType);
    }

    public static DRQuery getEnum(@NotNull String name) {
        DRQuery enumType;

        switch (name.toLowerCase()) {
            case "irfo operator expired":
                enumType = DRQuery.IRFO_OPERATOR_EXPIRED;
                break;
            case "irfo gv permit expired":
                enumType = DRQuery.IRFO_GV_PERMIT_EXPIRED;
                break;
            case "irfo psv authorisation expired":
                enumType = DRQuery.IRFO_PSV_AUTHORISATION_EXPIRED;
                break;
            case "irfo gv permit withdrawn pending or refused expired":
                enumType = DRQuery.IRFO_GV_PERMIT_WITHDRAWN_PENDING_OR_REFUSED_EXPIRED;
                break;
            case "licence not yet submitted":
                enumType = DRQuery.LICENCE_NOT_YET_SUBMITTED;
                break;
            case "applications refused":
                enumType = DRQuery.APPLICATIONS_REFUSED;
                break;
            default:
                throw new IllegalArgumentException(Output.printColoredLog("[ERROR] Unsupported name:" + name));
        }

        return enumType;
    }
}
