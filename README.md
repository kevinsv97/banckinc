@Modifying
    @Transactional // Asegúrate de que la operación esté en una transacción
    @Query(value = "INSERT INTO CO_EXTERNAL_SALE_LOG (DOCUMENT_NUMBER, CANAL, USER_IP, OFFICE, REGION, TRANSACTION_DATE, PRODUCT, ADVISOR_CODE, FINANCIAL_PRODUCT, FRANCHISE, CELLPHONE, OTP, REGION_CODE, OTP_VALIDATION_DATE, SALE_TYPE, BROWSER, DEVICE_TYPE) "
                 + "VALUES (:documentNumber, :canal, :userIp, :office, :region, :transactionDate, :product, :advisorCode, :financialProduct, :franchise, :cellphone, :otp, :regionCode, :otpValidationDate, :saleType, :browser, :deviceType)",
           nativeQuery = true)
    void insertSalesLog(
            @Param("documentNumber") int documentNumber,
            @Param("canal") String canal,
            @Param("userIp") String userIp,
            @Param("office") String office,
            @Param("region") String region,
            @Param("transactionDate") LocalDateTime transactionDate,
            @Param("product") int product,
            @Param("advisorCode") int advisorCode,
            @Param("financialProduct") String financialProduct,
            @Param("franchise") String franchise,
            @Param("cellphone") String cellphone,
            @Param("otp") int otp,
            @Param("regionCode") int regionCode,
            @Param("otpValidationDate") LocalDateTime otpValidationDate,
            @Param("saleType") String saleType,
            @Param("browser") String browser,
            @Param("deviceType") String deviceType);
}
Paso 2: Implementar el Servicio
Luego, puedes llamar a este método desde tu servicio para realizar la inserción. Aquí tienes un ejemplo:

java
Copy code
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SalesLogService {

    @Autowired
    private SalesLogRepository salesLogRepository;

    @Transactional
    public void insertarSalesLog(SalesLog salesLog) {
        salesLogRepository.insertSalesLog(
                salesLog.getDocumentNumber(),
                salesLog.getCanal(),
                salesLog.getUserIp(),
                salesLog.getOffice(),
                salesLog.getRegion(),
                salesLog.getTransactionDate(),
                salesLog.getProduct(),
                salesLog.getAdvisorCode(),
                salesLog.getFinancialProduct(),
                salesLog.getFranchise(),
                salesLog.getCellphone(),
                salesLog.getOtp(),
                salesLog.getRegionCode(),
                salesLog.getOtpValidationDate(),
                salesLog.getSaleType(),
                salesLog.getBrowser(),
                salesLog.getDeviceType());
    }
}
