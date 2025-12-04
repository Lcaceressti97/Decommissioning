package hn.com.tigo.inquiriesamsysnavega.services.interfaces;

import java.util.List;

import hn.com.tigo.inquiriesamsysnavega.models.NavegaBalancesModel;
import hn.com.tigo.inquiriesamsysnavega.models.TableNameModel;

public interface NavegaBalancesService {

	List<NavegaBalancesModel> getAllNavegaBalances();

	List<TableNameModel> getTableNamesQueryNavega();

	List<NavegaBalancesModel> getTableDataQueryNavega(String tableName);
	
	NavegaBalancesModel getBalanceByEbsAccount(String ebsAccount);
}
