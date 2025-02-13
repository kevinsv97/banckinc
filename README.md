CREATE OR REPLACE FUNCTION GET_RISK_JSON(
    p_dimension IN VARCHAR2 DEFAULT NULL,
    p_income IN NUMBER,
    p_partner_id IN NUMBER
) RETURN CLOB IS
    v_json CLOB;
BEGIN
    -- Construcción del JSON utilizando JSON_ARRAYAGG y JSON_OBJECT
    SELECT JSON_ARRAYAGG(
               JSON_OBJECT(
                   'dimension' VALUE d.dimension_name,
                   'income' VALUE TO_CHAR(p_income),
                   'rank' VALUE (
                       SELECT MIN_INCOME || ' - ' || MAX_INCOME
                       FROM BUSRULE_MT_INCOME_RANGE
                       WHERE p_income BETWEEN MIN_INCOME AND MAX_INCOME
                   ),
                   'riesgos' VALUE (
                       SELECT JSON_ARRAYAGG(
                                  JSON_OBJECT(
                                      'idTipoRiesgo' VALUE r.ID_TIPO_RIESGO,
                                      'name' VALUE r.NAME,
                                      'tope' VALUE r.TOPE
                                  )
                              )
                       FROM BUSRULE_MT_CAP_DIMENSION cd
                       JOIN BUSRULE_MT_RISK_TYPES r ON cd.RISK_TYPE_ID = r.RISK_TYPE_ID
                       WHERE cd.DIMENSION_ID = d.dimension_id
                         AND cd.INCOME_RANGE_ID = (
                             SELECT RANK_ID
                             FROM BUSRULE_MT_INCOME_RANGE
                             WHERE p_income BETWEEN MIN_INCOME AND MAX_INCOME
                         )
                         AND cd.PARTNER_ID = p_partner_id
                   )
               )
           )
    INTO v_json
    FROM (
        SELECT DISTINCT d.DIMENSION_ID, d.NAME AS dimension_name
        FROM BUSRULE_MT_DIMENSIONS d
        JOIN BUSRULE_MT_CAP_DIMENSION cd ON d.DIMENSION_ID = cd.DIMENSION_ID
        WHERE (p_dimension IS NULL AND cd.PARTNER_ID = p_partner_id)
           OR (p_dimension IS NOT NULL AND d.NAME = p_dimension AND cd.PARTNER_ID = p_partner_id)
    ) d;

    RETURN v_json;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN '[]'; -- JSON vacío si no hay resultados
END GET_RISK_JSON;
/
