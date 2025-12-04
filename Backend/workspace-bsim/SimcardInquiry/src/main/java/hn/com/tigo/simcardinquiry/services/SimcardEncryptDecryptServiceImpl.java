package hn.com.tigo.simcardinquiry.services;

import hn.com.tigo.simcardinquiry.models.SimcardEncryptDecryptRequest;
import hn.com.tigo.simcardinquiry.models.SimcardEncryptDecryptResponse;
import hn.com.tigo.simcardinquiry.services.interfaces.ILogsSimcardService;
import hn.com.tigo.simcardinquiry.services.interfaces.ISimcardEncryptDecryptService;
import hn.com.tigo.simcardinquiry.utils.Constants;
import hn.com.tigo.simcardinquiry.utils.EncryptDecrypt;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;

@Service
public class SimcardEncryptDecryptServiceImpl implements ISimcardEncryptDecryptService {

    private final ILogsSimcardService logsService;

    public SimcardEncryptDecryptServiceImpl(ILogsSimcardService logsService) {
        this.logsService = logsService;
    }

    @Override
    public SimcardEncryptDecryptResponse simcardEncrypt(SimcardEncryptDecryptRequest request) {
        try {
            return new SimcardEncryptDecryptResponse(
                    EncryptDecrypt.encript(request.getImsib()),
                    EncryptDecrypt.encript(request.getKi()),
                    EncryptDecrypt.encript(request.getPin1()),
                    EncryptDecrypt.encript(request.getPuk1()),
                    EncryptDecrypt.encript(request.getPin2()),
                    EncryptDecrypt.encript(request.getPuk2()),
                    EncryptDecrypt.encript(request.getAdm2()),
                    EncryptDecrypt.encript(request.getAdm3()),
                    EncryptDecrypt.encript(request.getAcc())
            );
        } catch (BadRequestException e) {
            logsService.saveLog(6, 1L, Constants.BAD_REQUEST_EXCEPTION + e.getMessage());
            throw e;

        } catch (Exception e) {
            throw new BadRequestException("Error encrypting data", e);
        }
    }

    @Override
    public SimcardEncryptDecryptResponse simcardDecrypt(SimcardEncryptDecryptRequest request) {
        try {
            return new SimcardEncryptDecryptResponse(
                    EncryptDecrypt.decript(request.getImsib()),
                    EncryptDecrypt.decript(request.getKi()),
                    EncryptDecrypt.decript(request.getPin1()),
                    EncryptDecrypt.decript(request.getPuk1()),
                    EncryptDecrypt.decript(request.getPin2()),
                    EncryptDecrypt.decript(request.getPuk2()),
                    EncryptDecrypt.decript(request.getAdm2()),
                    EncryptDecrypt.decript(request.getAdm3()),
                    EncryptDecrypt.decript(request.getAcc())
            );
        } catch (BadRequestException e) {
            logsService.saveLog(6, 1L, Constants.BAD_REQUEST_EXCEPTION + e.getMessage());
            throw e;

        } catch (Exception e) {
            throw new BadRequestException("Error decrypting data", e);
        }
    }
}

