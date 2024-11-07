{
  "batch_size": 100,                    // Tamaño de lote para procesamiento por lotes
  "field_count": 5,                     // Cantidad total de campos esperados en el objeto
  "field_validation": {                 // Objeto que agrupa toda la información de validación de campos
    "required_fields": [                // Lista de campos obligatorios
      "firstName",
      "lastName",
      "age",
      "policyNumber"
    ],
    "validation_fields": [              // Lista de campos a validar en general
      "firstName",
      "lastName",
      "age",
      "policyNumber",
      "email"
    ],
    "ownership_check_fields": [         // Campos adicionales para verificar propiedad de la póliza
      "policyOwnerName",
      "policyStartDate",
      "policyEndDate",
      "policyPremium"
    ]
  }
}
