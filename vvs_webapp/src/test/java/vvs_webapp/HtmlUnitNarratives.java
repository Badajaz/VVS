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
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class HtmlUnitNarratives {

	private static final String APPLICATION_URL = "http://localhost:8080/VVS_webappdemo/";
	private static final String idJoseFaria = "1";
	private static final String telJoseFaria = "914276732";
	private static final String nomeJoseFaria = "JOSE FARIA";
	private static final String vatJoseFaria = "197672337";
	private static final String ruaJoseFaria = "Rua do Ouro";
	private static final String doorJoseFaria = "12";
	private static final String postalCodeJoseFaria = "1600-477";
	private static final String localityCodeJoseFaria = "Lisboa";


	private static HtmlPage page;

	@BeforeClass
	public static void setUpClass() throws Exception {
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) { 

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
	}


	/**
	 * Caso para inserir um nova morada um utilizador já existente. 
	 */

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
		assertEquals("Enter Name", customerByVATPage.getTitleText());
		HtmlForm customerByVATForm = customerByVATPage.getForms().get(0);
		HtmlInput vatInput = customerByVATForm.getInputByName("vat");
		vatInput.setValueAttribute(vatJoseFaria);
		HtmlInput submit = customerByVATForm.getInputByName("submit");
		HtmlPage resultado = null;


		String textReportPage = customerByVATPage.asText();
		assertTrue(textReportPage.contains(vatJoseFaria));

		HtmlPage reportPage = null;
		try(final WebClient webClient = new WebClient(BrowserVersion.getDefault())){
			java.net.URL url = null;
			try {
				url = new java.net.URL(APPLICATION_URL+"GetCustomerPageController");
			} catch (MalformedURLException e) {
				System.err.println("Pagina inexistente " + e.getMessage());
			}
			WebRequest requestSettings = new WebRequest(url,HttpMethod.GET);
			requestSettings.setRequestParameters(new ArrayList<NameValuePair>());
			requestSettings.getRequestParameters().add(new NameValuePair("vat", vatJoseFaria));
			requestSettings.getRequestParameters().add(new NameValuePair("submit", "Get+Customer"));

			try {
				reportPage = webClient.getPage(requestSettings);
			} catch (FailingHttpStatusCodeException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		assertTrue(reportPage.asXml().contains(nomeJoseFaria));

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

		HtmlForm addCustomerForm = addAddressToCustomerPage.getForms().get(0);
		HtmlInput vatInputAdress = addCustomerForm.getInputByName("vat");
		vatInputAdress.setValueAttribute(vatJoseFaria);
		HtmlInput adressInputAdress = addCustomerForm.getInputByName("address");
		adressInputAdress.setValueAttribute(ruaJoseFaria);
		HtmlInput doorInputAdress = addCustomerForm.getInputByName("door");
		doorInputAdress.setValueAttribute(doorJoseFaria);
		HtmlInput postalCodeInputAdress = addCustomerForm.getInputByName("postalCode");
		postalCodeInputAdress.setValueAttribute(postalCodeJoseFaria);
		HtmlInput localityInputAdress = addCustomerForm.getInputByName("locality");
		localityInputAdress.setValueAttribute(localityCodeJoseFaria);

		//o butão não tem name 
		HtmlInput insert = addCustomerForm.getInputByValue("Insert");


		String reportPageAddressText = addAddressToCustomerPage.asText();
		assertTrue(reportPageAddressText.contains(vatJoseFaria));
		assertTrue(reportPageAddressText.contains(ruaJoseFaria));
		assertTrue(reportPageAddressText.contains(doorJoseFaria));
		assertTrue(reportPageAddressText.contains(postalCodeJoseFaria));
		assertTrue(reportPageAddressText.contains(localityCodeJoseFaria));

		HtmlPage report = null;

		try(final WebClient webClient = new WebClient(BrowserVersion.getDefault())){
			java.net.URL url = null;
			try {
				url = new java.net.URL(APPLICATION_URL+"GetCustomerPageController");
			} catch (MalformedURLException e) {
				System.err.println("Pagina inexistente " + e.getMessage());
			}
			WebRequest requestSettings = new WebRequest(url,HttpMethod.POST);
			requestSettings.setRequestParameters(new ArrayList<NameValuePair>());
			requestSettings.getRequestParameters().add(new NameValuePair("vat", vatJoseFaria));
			requestSettings.getRequestParameters().add(new NameValuePair("address", ruaJoseFaria));
			requestSettings.getRequestParameters().add(new NameValuePair("door", doorJoseFaria));
			requestSettings.getRequestParameters().add(new NameValuePair("postalCode", postalCodeJoseFaria));
			requestSettings.getRequestParameters().add(new NameValuePair("locality", localityCodeJoseFaria));
			requestSettings.getRequestParameters().add(new NameValuePair("submit", "Get+Customer"));

			try {
				report = webClient.getPage(requestSettings);
			} catch (FailingHttpStatusCodeException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		assertTrue(report.asXml().contains(nomeJoseFaria));


		int adressCount2 = 0; 

		if(report.asXml().contains("<table")) {
			List<HtmlElement> items = report.getByXPath("//tr");
			adressCount2 = items.size()-1;
		}
		System.out.println("adressCount = "+adressCount);
		System.out.println("adressCount2 = "+adressCount2);
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


		HtmlInput getCustomer = insertCustomerForm.getInputByName("submit");


		HtmlPage report = null;
		try(final WebClient webClient = new WebClient(BrowserVersion.getDefault())){
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
		}
		List<Object> mensagemErro = null;
		if(report.asXml().contains("<li>")) {
			//obter o nome do 1º elemento
			mensagemErro = report.getByXPath("//li/text ()");
		}
		Object mensagemErroSuposta = "It was not possible to fulfill the request: Can't add customer with vat number "+vatJoseFaria+".";
		assertTrue(mensagemErro.get(0).toString().equals(mensagemErroSuposta));

		//o erro vai para o controller AddCustomerPageController

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

		HtmlInput submit = addCustomerForm.getInputByName("submit");
		HtmlPage reportPage = null;

		try {
			reportPage = submit.click();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String textReportPage = reportPage.asText();
		assertTrue(textReportPage.contains(vatNewCustomer));
		assertTrue(textReportPage.contains(designationNewCustomer));
		assertTrue(textReportPage.contains(phoneNewCustomer));

		HtmlPage report = null;
		try(final WebClient webClient = new WebClient(BrowserVersion.getDefault())){
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
		}

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

		HtmlInput vatInputRemove = addCustomerForm.getInputByName("vat");
		vatInput.setValueAttribute(vatNewCustomer);

		HtmlInput remove = addCustomerForm.getInputByName("submit");


		String textReportPageRemove = removeCustomerPage.asText();
		assertTrue(textReportPageRemove.contains(vatNewCustomer));

		HtmlPage reportRemove = null;
		try(final WebClient webClient = new WebClient(BrowserVersion.getDefault())){
			java.net.URL url = null;
			try {
				url = new java.net.URL(APPLICATION_URL+"RemoveCustomerPageController");
			} catch (MalformedURLException e) {
				System.err.println("Pagina inexistente " + e.getMessage());
			}
			WebRequest requestSettings = new WebRequest(url,HttpMethod.GET);
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
		}

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

		HtmlInput getSale = saleListForm.getInputByValue("Add Sale");
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
			req = new WebRequest(new java.net.URL(APPLICATION_URL+"GetSalePageController"),
					HttpMethod.GET);
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

		assumeTrue(reportPage.asXml().contains("<table"));
		List<HtmlElement> items = reportPage.getByXPath("//tr");

		webClient.close();
		return items.size()-1;
	}

	// @return 0 - nome, 1 - phone, 2 - vat

	private String [] getFirstCustomerInfo() {
		HtmlAnchor listAllCustomersLink = page.getAnchorByHref("GetAllCustomersPageController");
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
