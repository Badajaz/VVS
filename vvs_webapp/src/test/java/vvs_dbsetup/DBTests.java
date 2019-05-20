package vvs_dbsetup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static vvs_dbsetup.DBSetupUtils.DB_PASSWORD;
import static vvs_dbsetup.DBSetupUtils.DB_URL;
import static vvs_dbsetup.DBSetupUtils.DB_USERNAME;
import static vvs_dbsetup.DBSetupUtils.DELETE_ALL;
import static vvs_dbsetup.DBSetupUtils.INSERT_CUSTOMER_ADDRESS_DATA;
import static vvs_dbsetup.DBSetupUtils.INSERT_CUSTOMER_SALE_DATA;

import static vvs_dbsetup.DBSetupUtils.startApplicationDatabaseForTesting;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;

import webapp.persistence.PersistenceException;
import webapp.services.ApplicationException;
import webapp.services.CustomerDTO;
import webapp.services.CustomerService;
import webapp.services.CustomersDTO;
import webapp.services.SaleDTO;
import webapp.services.SaleDeliveryDTO;
import webapp.services.SaleService;

public class DBTests {
	private static Destination dataSource;

	// the tracker is static because JUnit uses a separate Test instance for every test method.
	private static DbSetupTracker dbSetupTracker = new DbSetupTracker();

	@BeforeClass
	public static void setupClass() {
		startApplicationDatabaseForTesting();
		dataSource = DriverManagerDestination.with(DB_URL, DB_USERNAME, DB_PASSWORD);
	}

	@Before
	public void setup() throws SQLException {

		Operation initDBOperations = Operations.sequenceOf(
				DELETE_ALL
				, INSERT_CUSTOMER_ADDRESS_DATA
				, INSERT_CUSTOMER_SALE_DATA
				);

		DbSetup dbSetup = new DbSetup(dataSource, initDBOperations);

		// Use the tracker to launch the DbSetup. This will speed-up tests 
		// that do not not change the BD. Otherwise, just use dbSetup.launch();
		dbSetupTracker.launchIfNecessary(dbSetup);

	}

	@Test
	public void updateCustomerContact() throws ApplicationException {

		final int newPhoneNumber = 913885916;
		CustomerService service = CustomerService.INSTANCE;
		CustomerDTO customer = service.getAllCustomers().customers.get(0);
		int vat = customer.vat;
		int previousPhone = service.getCustomerByVat(vat).phoneNumber;
		service.updateCustomerPhone(vat, newPhoneNumber);
		int currentPhone = service.getCustomerByVat(vat).phoneNumber;

		boolean result = previousPhone == currentPhone;
		assertFalse(result);

	}

	@Test
	public void DeleteAllCustomersButOneAndCheckIfThatOneRemains() throws ApplicationException {
		CustomersDTO listaCustomers = CustomerService.INSTANCE.getAllCustomers();

		for(int i = 0; i < listaCustomers.customers.size()-1;i++ ) {
			CustomerService.INSTANCE.removeCustomer(listaCustomers.customers.get(i).vat);
		}
		CustomersDTO listaCustomers2 = CustomerService.INSTANCE.getAllCustomers();

		int posicaoLastListaCustomers = listaCustomers.customers.size()-1;
		assertEquals( listaCustomers.customers.get(posicaoLastListaCustomers).vat , listaCustomers2.customers.get(0).vat );

	}

	@Test
	public void checkIfDeleteDeliverysAfterDelitingCustomer() throws ApplicationException {

		CustomersDTO listaCustomers = CustomerService.INSTANCE.getAllCustomers();
		int vatCustomer =  listaCustomers.customers.get(0).vat;
		CustomerService.INSTANCE.removeCustomer(vatCustomer);
		List<SaleDeliveryDTO> deliverys = SaleService.INSTANCE.getSalesDeliveryByVat(vatCustomer).sales_delivery;
		System.out.println(deliverys);
		assertEquals(deliverys.size(),0);
	}


	@Test(expected =  Test.None.class)
	public void checkIfDeletingCustomerAndAddingAgainDoesNoTHrowException() throws ApplicationException {

		CustomersDTO listaCustomers = CustomerService.INSTANCE.getAllCustomers();
		int vatCustomer =  listaCustomers.customers.get(0).vat;
		String designationCustomer =  listaCustomers.customers.get(0).designation;
		int phoneNumberCustomer =  listaCustomers.customers.get(0).phoneNumber;

		CustomerService.INSTANCE.removeCustomer(vatCustomer);
		CustomerService.INSTANCE.addCustomer(vatCustomer, designationCustomer, phoneNumberCustomer);
	}

	@Test
	public void testIfAddingDeliveryIncreasesTheirSizeByOne() throws ApplicationException {

		CustomersDTO listaCustomers = CustomerService.INSTANCE.getAllCustomers();
		int vatCustomer = 0;
		List<SaleDTO> sales = null;
		int i = 0;
		while(i != listaCustomers.customers.size() && (sales == null || sales.size() == 0)) {
			vatCustomer =  listaCustomers.customers.get(i).vat; 
			sales = SaleService.INSTANCE.getSaleByCustomerVat(vatCustomer).sales;
			i++;
		}
		assumeTrue(sales.size() != 0);
		int saleId = sales.get(0).id;
		int salesDeliverySize = SaleService.INSTANCE.getSalesDeliveryByVat(vatCustomer).sales_delivery.size();
		int adress = CustomerService.INSTANCE.getAllAddresses(vatCustomer).addrs.get(0).id;
		SaleService.INSTANCE.addSaleDelivery(saleId, adress);
		List<SaleDeliveryDTO> salesDelivery2 = SaleService.INSTANCE.getSalesDeliveryByVat(vatCustomer).sales_delivery;
		assertEquals(salesDeliverySize+1,salesDelivery2.size());


	}

	///------------ DOIS TESTES EXTRA


	@Test
	public void addSaleAndCheckIfSalesIncreaseByOne() throws ApplicationException {
		List<CustomerDTO> listaCustomers = CustomerService.INSTANCE.getAllCustomers().customers;
		int customerVat =  listaCustomers.get(0).vat;
		List<SaleDTO> listaSales = SaleService.INSTANCE.getSaleByCustomerVat(customerVat).sales;
		SaleService.INSTANCE.addSale(customerVat);
		List<SaleDTO> listaSales2 = SaleService.INSTANCE.getSaleByCustomerVat(customerVat).sales;
		assertEquals(listaSales.size()+1,listaSales2.size());
	}

	@Test
	public void CheckIfSaleIsDeletedDeliveryIsDeletedToo() throws ApplicationException, PersistenceException {
		List<CustomerDTO> listaCustomers = CustomerService.INSTANCE.getAllCustomers().customers;
		int i = 0;
		int vatCustomer = 0;
		List<SaleDTO> sales = null;
		while(i != listaCustomers.size() && (sales == null || sales.size() == 0)) {
			vatCustomer =  listaCustomers.get(i).vat; 
			sales = SaleService.INSTANCE.getSaleByCustomerVat(vatCustomer).sales;
			i++;
		}

		assumeTrue(sales.size() != 0);
		List<SaleDeliveryDTO> deliveries = SaleService.INSTANCE.getSalesDeliveryByVat(vatCustomer).sales_delivery;
		System.out.println(deliveries.size());
		assumeTrue(deliveries.size() != 0);
		SaleService.INSTANCE.removeSaleByVat(vatCustomer);
		List<SaleDeliveryDTO> deliveries2 = SaleService.INSTANCE.getSalesDeliveryByVat(vatCustomer).sales_delivery;
		System.out.println(deliveries2.size());
		assertEquals(deliveries2.size(),0);
		
	}



}
