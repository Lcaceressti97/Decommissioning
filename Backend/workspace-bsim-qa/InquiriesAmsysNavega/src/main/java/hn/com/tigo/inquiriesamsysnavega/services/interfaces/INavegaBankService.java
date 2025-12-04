package hn.com.tigo.inquiriesamsysnavega.services.interfaces;

import java.util.List;

import hn.com.tigo.inquiriesamsysnavega.models.NavegaBankModel;

public interface INavegaBankService {

	List<NavegaBankModel> getAllNavegaBank();

	NavegaBankModel getNavegaBankById(Long id);

	NavegaBankModel getBankByIdentifyingLetter(String identifyingLetter);

	void addNavegaBank(NavegaBankModel model);

	void updateNavegaBank(Long id, NavegaBankModel model);

	void deleteNavegaBank(Long id);

}
