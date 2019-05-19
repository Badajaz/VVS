package vvs_dbsetup;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static vvs_dbsetup.DBSetupUtils.DB_PASSWORD;
import static vvs_dbsetup.DBSetupUtils.DB_URL;
import static vvs_dbsetup.DBSetupUtils.DB_USERNAME;
import static vvs_dbsetup.DBSetupUtils.DELETE_ALL;
import static vvs_dbsetup.DBSetupUtils.INSERT_CUSTOMER_ADDRESS_DATA;
import static vvs_dbsetup.DBSetupUtils.startApplicationDatabaseForTesting;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;

import webapp.services.ApplicationException;
import webapp.services.CustomerService;

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
			);
		
		DbSetup dbSetup = new DbSetup(dataSource, initDBOperations);
		
        // Use the tracker to launch the DbSetup. This will speed-up tests 
		// that do not not change the BD. Otherwise, just use dbSetup.launch();
        dbSetupTracker.launchIfNecessary(dbSetup);
		
	}
	
	@Test
	public void updateCustomerContact() {
		CustomerService service = CustomerService.INSTANCE;
		int vat = 197672337;
		int newPhoneNumber = 913885916;
		try {
			
			int previousPhone = service.getCustomerByVat(vat).phoneNumber;
			System.out.println(previousPhone);
			service.updateCustomerPhone(vat, newPhoneNumber);
			int currentPhone = service.getCustomerByVat(vat).phoneNumber;
			System.out.println(currentPhone);
			assertFalse(previousPhone == currentPhone);
			
		} catch (ApplicationException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
}
