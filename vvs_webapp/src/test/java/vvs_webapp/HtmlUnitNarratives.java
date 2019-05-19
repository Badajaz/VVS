package vvs_webapp;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class HtmlUnitNarratives {

	private static final String APPLICATION_URL = "http://localhost:8080/VVS_webappdemo/";
	private static final WebClient webClient = new WebClient(BrowserVersion.getDefault());
	private static HtmlPage page;

	@BeforeClass
	public static void setUpClass() throws Exception {
		// possible configurations needed to prevent JUnit tests to fail for complex HTML pages
		webClient.setJavaScriptTimeout(15000);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setThrowExceptionOnScriptError(false);

		page = webClient.getPage(APPLICATION_URL);
		assertEquals(200, page.getWebResponse().getStatusCode()); // OK status
	}
	@Test
	public void insertNewAddressForExistingCustomer() {

		int adressCount=0;

		assertEquals("WebAppDemo Menu", page.getTitleText());
		HtmlAnchor customerByVATLink = page.getAnchorByHref("getCustomerByVAT.html");
		HtmlPage customerByVATPage = null;
		try {
			customerByVATPage = (HtmlPage)customerByVATLink.openLinkInNewWindow();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.err.println("link para a pagina errada");
		}
		String[] customerInfo = getFirstCustomerInfo();
		String nome = customerInfo[0];
		String vat = customerInfo[2];

		assertEquals("Enter Name", customerByVATPage.getTitleText());
		HtmlForm customerByVATForm = customerByVATPage.getForms().get(0);
		HtmlInput vatInput = customerByVATForm.getInputByName("vat");
		vatInput.setValueAttribute(vat);

		String customerByVATPageText = customerByVATPage.asText();
		assertTrue(customerByVATPageText.contains(vat));

		HtmlPage reportPage = null;
		java.net.URL url = null;
		try {
			url = new java.net.URL(APPLICATION_URL+"GetCustomerPageController");
		} catch (MalformedURLException e) {
			System.err.println("Pagina inexistente " + e.getMessage());
		}
		WebRequest requestSettings = new WebRequest(url,HttpMethod.GET);
		requestSettings.setRequestParameters(new ArrayList<NameValuePair>());
		requestSettings.getRequestParameters().add(new NameValuePair("vat", vat));
		requestSettings.getRequestParameters().add(new NameValuePair("submit", "Get+Customer"));

		try {
			reportPage = webClient.getPage(requestSettings);
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(200, reportPage.getWebResponse().getStatusCode());
		assertTrue(reportPage.asXml().contains(nome));

		//não contem a tabela
		if(reportPage.asXml().contains("<table")) {
			List<HtmlElement> items = reportPage.getByXPath("//tr");
			adressCount = items.size()-1;
		}

		HtmlAnchor addAddressToCustomer  = page.getAnchorByHref("addAddressToCustomer.html");
		HtmlPage addAddressToCustomerPage = null;
		try {
			addAddressToCustomerPage = (HtmlPage) addAddressToCustomer.openLinkInNewWindow();
		} catch (MalformedURLException e) {
			System.err.println("Pagina address inexistente " + e.getMessage());
		}

		assertEquals("Enter Address", addAddressToCustomerPage.getTitleText());

		String rua = "Rua do Ouro";
		String door = "12";
		String postalCode = "1600-477";
		String localityCode = "Lisboa";

		HtmlForm addCustomerForm = addAddressToCustomerPage.getForms().get(0);
		HtmlInput vatInputAdress = addCustomerForm.getInputByName("vat");
		vatInputAdress.setValueAttribute(vat);
		HtmlInput adressInputAdress = addCustomerForm.getInputByName("address");

		adressInputAdress.setValueAttribute(rua);
		HtmlInput doorInputAdress = addCustomerForm.getInputByName("door");
		doorInputAdress.setValueAttribute(door);
		HtmlInput postalCodeInputAdress = addCustomerForm.getInputByName("postalCode");
		postalCodeInputAdress.setValueAttribute(postalCode);
		HtmlInput localityInputAdress = addCustomerForm.getInputByName("locality");
		localityInputAdress.setValueAttribute(localityCode);

		String addAddressToCustomerText = addAddressToCustomerPage.asText();
		assertTrue(addAddressToCustomerText.contains(vat));
		assertTrue(addAddressToCustomerText.contains(rua));
		assertTrue(addAddressToCustomerText.contains(door));
		assertTrue(addAddressToCustomerText.contains(postalCode));
		assertTrue(addAddressToCustomerText.contains(localityCode));

		HtmlPage report = null;
		try {
			url = new java.net.URL(APPLICATION_URL+"GetCustomerPageController");
		} catch (MalformedURLException e) {
			System.err.println("Pagina inexistente " + e.getMessage());
		}
		requestSettings = new WebRequest(url,HttpMethod.POST);
		requestSettings.setRequestParameters(new ArrayList<NameValuePair>());
		requestSettings.getRequestParameters().add(new NameValuePair("vat", vat));
		requestSettings.getRequestParameters().add(new NameValuePair("address", rua));
		requestSettings.getRequestParameters().add(new NameValuePair("door", door));
		requestSettings.getRequestParameters().add(new NameValuePair("postalCode", postalCode));
		requestSettings.getRequestParameters().add(new NameValuePair("locality", localityCode));
		requestSettings.getRequestParameters().add(new NameValuePair("submit", "Get+Customer"));
		try {
			report = webClient.getPage(requestSettings);
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(200, report.getWebResponse().getStatusCode());
		assertTrue(report.asXml().contains(nome));

		int adressCount2 = 0; 

		if(report.asXml().contains("<table")) {
			List<HtmlElement> items = report.getByXPath("//tr");
			adressCount2 = items.size()-1;
		}
		assertEquals(adressCount2, adressCount+1);
	}
	@Test
	public void InsertExistingCustomerAndCheckIfReturnsError() {

		assertEquals("WebAppDemo Menu", page.getTitleText());

		String[] customerInfo = getFirstCustomerInfo();
		String nome = customerInfo[0];
		String phone = customerInfo[1];
		String vat = customerInfo[2];

		HtmlAnchor insertCustomer = page.getAnchorByHref("addCustomer.html");
		HtmlPage insertCustomerPage = null;
		try {
			insertCustomerPage = (HtmlPage)insertCustomer.openLinkInNewWindow();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.err.println("link para a pagina errada");
		}
		assertEquals("Enter Name", insertCustomerPage.getTitleText());

		HtmlForm insertCustomerForm = insertCustomerPage.getForms().get(0);
		HtmlInput vatInput = insertCustomerForm.getInputByName("vat");
		vatInput.setValueAttribute(vat);
		HtmlInput designationInput = insertCustomerForm.getInputByName("designation");
		designationInput.setValueAttribute(nome);
		HtmlInput phoneInput = insertCustomerForm.getInputByName("phone");
		phoneInput.setValueAttribute(phone);

		String insertCustomerPageText = insertCustomerPage.asText();

		assertTrue(insertCustomerPageText.contains(vat));
		assertTrue(insertCustomerPageText.contains(nome));
		assertTrue(insertCustomerPageText.contains(phone));

		HtmlPage report = null;
		java.net.URL url = null;
		try {
			url = new java.net.URL(APPLICATION_URL+"AddCustomerPageController");
		} catch (MalformedURLException e) {
			System.err.println("Pagina inexistente " + e.getMessage());
		}
		WebRequest requestSettings = new WebRequest(url,HttpMethod.POST);
		requestSettings.setRequestParameters(new ArrayList<NameValuePair>());
		requestSettings.getRequestParameters().add(new NameValuePair("vat", vat));
		requestSettings.getRequestParameters().add(new NameValuePair("designation", nome));
		requestSettings.getRequestParameters().add(new NameValuePair("phone", phone));
		requestSettings.getRequestParameters().add(new NameValuePair("submit", "Get+Customer"));

		try {
			report = webClient.getPage(requestSettings);
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(200, report.getWebResponse().getStatusCode());

		List<Object> mensagemErro = null;
		if(report.asXml().contains("<li>")) {
			//obter o nome do 1º elemento
			mensagemErro = report.getByXPath("//li/text ()");
		}
		Object mensagemErroSuposta = "It was not possible to fulfill the request: Can't add customer with vat number "+vat+".";
		assertTrue(mensagemErro.get(0).toString().equals(mensagemErroSuposta));
	}
	@Test
	public void createAndRemoveCustomer() {

		final String vatNewCustomer = "123456789";
		final String designationNewCustomer = "Manuel Ferreira";
		final String phoneNewCustomer = "912345678";

		assertEquals("WebAppDemo Menu", page.getTitleText());

		HtmlAnchor addCustomer = page.getAnchorByHref("addCustomer.html");

		HtmlPage addCustomerPage = null;
		try {
			addCustomerPage = (HtmlPage)addCustomer.openLinkInNewWindow();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.err.println("link para a pagina errada");
		}
		assertEquals("Enter Name", addCustomerPage.getTitleText());

		HtmlForm addCustomerForm = addCustomerPage.getForms().get(0);

		HtmlInput vatInput = addCustomerForm.getInputByName("vat");
		vatInput.setValueAttribute(vatNewCustomer);

		HtmlInput designationInput = addCustomerForm.getInputByName("designation");
		designationInput.setValueAttribute(designationNewCustomer);

		HtmlInput phoneInput = addCustomerForm.getInputByName("phone");
		phoneInput.setValueAttribute(phoneNewCustomer);
		
		String textReportPage = addCustomerPage.asText();
		assertTrue(textReportPage.contains(vatNewCustomer));
		assertTrue(textReportPage.contains(designationNewCustomer));
		assertTrue(textReportPage.contains(phoneNewCustomer));

		HtmlPage report = null;
		java.net.URL url = null;
		try {
			url = new java.net.URL(APPLICATION_URL+"AddCustomerPageController");
		} catch (MalformedURLException e) {
			System.err.println("Pagina inexistente " + e.getMessage());
		}
		WebRequest requestSettings = new WebRequest(url,HttpMethod.POST);
		requestSettings.setRequestParameters(new ArrayList<NameValuePair>());
		requestSettings.getRequestParameters().add(new NameValuePair("vat", vatNewCustomer));
		requestSettings.getRequestParameters().add(new NameValuePair("designation", designationNewCustomer));
		requestSettings.getRequestParameters().add(new NameValuePair("phone", phoneNewCustomer));
		requestSettings.getRequestParameters().add(new NameValuePair("submit", "Get+Customer"));

		try {
			report = webClient.getPage(requestSettings);
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(200, report.getWebResponse().getStatusCode());

		HtmlAnchor removeCustomerLink = page.getAnchorByHref("RemoveCustomerPageController");
		HtmlPage removeCustomerPage = null;
		try {
			removeCustomerPage = (HtmlPage) removeCustomerLink.openLinkInNewWindow();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		// check if title is the one expected
		assertEquals("Enter VatNumber", removeCustomerPage.getTitleText());

		HtmlForm removeCustomerForm = removeCustomerPage.getForms().get(0);
		HtmlInput vatInputRemove = removeCustomerForm.getInputByName("vat");
		vatInputRemove.setValueAttribute(vatNewCustomer);
		String textReportPageRemove = removeCustomerPage.asText();
		assertTrue(textReportPageRemove.contains(vatNewCustomer));

		HtmlPage reportRemove = null;
		try {
			url = new java.net.URL(APPLICATION_URL+"RemoveCustomerPageController");
		} catch (MalformedURLException e) {
			System.err.println("Pagina inexistente " + e.getMessage());
		}
		requestSettings = new WebRequest(url,HttpMethod.GET);
		requestSettings.setRequestParameters(new ArrayList<NameValuePair>());
		requestSettings.getRequestParameters().add(new NameValuePair("vat", vatNewCustomer));
		requestSettings.getRequestParameters().add(new NameValuePair("submit", "Get+Customer"));

		try {
			reportRemove = webClient.getPage(requestSettings);
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(200, reportRemove.getWebResponse().getStatusCode());

		HtmlAnchor listAllCustomersLink = page.getAnchorByHref("GetAllCustomersPageController");
		HtmlPage listAllCustomersPage = null;
		try {
			listAllCustomersPage = (HtmlPage) listAllCustomersLink.openLinkInNewWindow();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		assertEquals("Customers Info", listAllCustomersPage.getTitleText());

		if (listAllCustomersPage.asXml().contains("<table")) {
			assertFalse(listAllCustomersPage.asXml().contains(vatNewCustomer));
		}
	}
	@Test
	public void NewSaleForExistingCustomerWithDelivery() {
		assertEquals("WebAppDemo Menu", page.getTitleText());

		String vat = getFirstCustomerInfo()[2];

		int numeroSalesAntes = getCustomerSalesTotalByVAT(vat,page);

		HtmlAnchor newSaleLink = page.getAnchorByHref("addSale.html");
		HtmlPage newSalePage = null;
		try {
			newSalePage = (HtmlPage) newSaleLink.openLinkInNewWindow();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		assertEquals("New Sale", newSalePage.getTitleText());
		HtmlForm newSaleForm = newSalePage.getForms().get(0);
		HtmlInput vatInputNewSale = newSaleForm.getInputByName("customerVat");
		vatInputNewSale.setValueAttribute(vat);

		HtmlInput getSale = newSaleForm.getInputByValue("Add Sale");
		HtmlPage getSaleSubmit = null;
		try {
			getSaleSubmit = getSale.click();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String textSaleSubmit = getSaleSubmit.asText();
		assertTrue(textSaleSubmit.contains(vat));

		HtmlPage reportPage = null;
		String formData = String.format("vat=%s", vat);
		WebRequest req = null;
		try {
			req = new WebRequest(new java.net.URL(APPLICATION_URL+"AddSalePageController"),
					HttpMethod.POST);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		req.setRequestBody(formData);
		WebClient webClient = null;

		try { 
			webClient = new WebClient(BrowserVersion.getDefault());
			reportPage = (HtmlPage) webClient.getPage(req);
		} catch (FailingHttpStatusCodeException | IOException e) {
			e.printStackTrace();
		}
		String textReportPage = reportPage.asText();
		assertTrue(textReportPage.contains(vat));
		assertEquals("Sales Info", reportPage.getTitleText());
		assertTrue(reportPage.asXml().contains("<table"));
		List<HtmlElement> items = reportPage.getByXPath("//tr");

		webClient.close();
		assertTrue(numeroSalesAntes==items.size());



		HtmlAnchor saleDeliveryLink = page.getAnchorByHref("saleDeliveryVat.html");
		HtmlPage saleDeliveryPage = null;
		try {
			saleDeliveryPage = (HtmlPage) saleDeliveryLink.openLinkInNewWindow();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		assertEquals("Enter Name", saleDeliveryPage.getTitleText());
		HtmlForm saleDeliveryForm = saleDeliveryPage.getForms().get(0);
		HtmlInput vatInputSaleDelivery = newSaleForm.getInputByName("vat");
		vatInputSaleDelivery.setValueAttribute(vat);

		HtmlInput getDelivery = saleDeliveryForm.getInputByValue("Get Customer");
		HtmlPage getDeliverySubmit = null;
		try {
			getDeliverySubmit = getDelivery.click();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String textDeliverySubmit = getDeliverySubmit.asText();
		assertTrue(textDeliverySubmit.contains(vat));
		//-------------------


		HtmlPage reportPageDelivery = null;
		String formDataDelivery = String.format("vat=%s", vat);
		WebRequest reqDelivery = null;
		try {
			reqDelivery = new WebRequest(new java.net.URL(APPLICATION_URL+"AddSaleDeliveryPageController"),
					HttpMethod.POST);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		reqDelivery.setRequestBody(formDataDelivery);
		WebClient webClientDelivery = null;

		try { 
			webClientDelivery = new WebClient(BrowserVersion.getDefault());
			reportPageDelivery = (HtmlPage) webClientDelivery.getPage(req);
		} catch (FailingHttpStatusCodeException | IOException e) {
			e.printStackTrace();
		}
		String textReportPageDelivery = reportPageDelivery.asText();

		assertTrue(textReportPageDelivery.contains(vat));
		assertEquals("Enter Name", reportPageDelivery.getTitleText());

		HtmlForm reportPageDeliveryForm = reportPageDelivery.getForms().get(0);

		HtmlInput vatInput = reportPageDeliveryForm.getInputByName("vat");
		vatInput.setValueAttribute(vat);

		/**
	 * 
	 * se não existir a tabela nesta pagina com os address criar um address
	 * 
	 */


	
		webClient.close();
	}

	private int getCustomerSalesTotalByVAT(String vat, HtmlPage page) {
		HtmlAnchor salesListLink = page.getAnchorByHref("getSales.html");
		HtmlPage salesListPage = null;
		try {
			salesListPage = (HtmlPage) salesListLink.openLinkInNewWindow();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		assertEquals("Enter Name", salesListPage.getTitleText());
		HtmlForm saleListForm = salesListPage.getForms().get(0);
		HtmlInput vatInput = saleListForm.getInputByName("customerVat");
		vatInput.setValueAttribute(vat);

		HtmlInput getSale = saleListForm.getInputByValue("Get Sales");
		HtmlPage getSaleSubmit = null;
		try {
			getSaleSubmit = getSale.click();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String textSaleSubmit = getSaleSubmit.asText();
		assertTrue(textSaleSubmit.contains(vat));

		HtmlPage reportPage = null;
		String formData = String.format("customerVat=%s", vat);

		WebRequest req = null;
		try {
			req = new WebRequest(new java.net.URL(APPLICATION_URL+"GetSalePageController"),
					HttpMethod.GET);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		req.setRequestBody(formData);
		try { 
			reportPage = (HtmlPage) webClient.getPage(req);
		} catch (FailingHttpStatusCodeException | IOException e) {
			e.printStackTrace();
		}
		String textReportPage = reportPage.asText();
		assertTrue(textReportPage.contains(vat));
		assertEquals("Sales Info", reportPage.getTitleText());

		assumeTrue(reportPage.asXml().contains("<table"));
		List<HtmlElement> items = reportPage.getByXPath("//tr");

		return items.size()-1;
	}

	// @return 0 - nome, 1 - phone, 2 - vat

	private String [] getFirstCustomerInfo() {
		HtmlAnchor listAllCustomersLink	 = page.getAnchorByHref("GetAllCustomersPageController");
		HtmlPage listAllCustomersPage = null;
		try {
			listAllCustomersPage = (HtmlPage) listAllCustomersLink.openLinkInNewWindow();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		assertEquals("Customers Info", listAllCustomersPage.getTitleText());
		assumeTrue(listAllCustomersPage.asXml().contains("<table"));
		String [] firstCustomerInfo= new String [3];
		firstCustomerInfo [0] = listAllCustomersPage.getByXPath("//tr[2]/td[1]/text()").get(0).toString();
		firstCustomerInfo [1] = listAllCustomersPage.getByXPath("//tr[2]/td[2]/text()").get(0).toString();
		firstCustomerInfo [2] = listAllCustomersPage.getByXPath("//tr[2]/td[3]/text()").get(0).toString();
		return firstCustomerInfo;
	}
}
