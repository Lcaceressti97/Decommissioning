package hn.com.tigo.simcardinquiry.services.interfaces;

import hn.com.tigo.simcardinquiry.models.SimcardEncryptDecryptRequest;
import hn.com.tigo.simcardinquiry.models.SimcardEncryptDecryptResponse;

public interface ISimcardEncryptDecryptService {

    SimcardEncryptDecryptResponse simcardEncrypt(SimcardEncryptDecryptRequest request);

    SimcardEncryptDecryptResponse simcardDecrypt(SimcardEncryptDecryptRequest request);

}
