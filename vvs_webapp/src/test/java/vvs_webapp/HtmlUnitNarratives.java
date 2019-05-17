package vvs_webapp;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		try {
			resultado  = submit.click();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("não deu submit");
		}
		String textReportPage = resultado.asText();
		//System.out.println("textReportPage = "+textReportPage);
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
		System.out.println("adressCount = "+adressCount);

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
		vatInput.setValueAttribute(vatJoseFaria);
		HtmlInput adressInputAdress = addCustomerForm.getInputByName("address");
		vatInput.setValueAttribute(ruaJoseFaria);
		HtmlInput doorInputAdress = addCustomerForm.getInputByName("door");
		vatInput.setValueAttribute(doorJoseFaria);
		HtmlInput postalCodeInputAdress = addCustomerForm.getInputByName("postalCode");
		vatInput.setValueAttribute(postalCodeJoseFaria);
		HtmlInput localityInputAdress = addCustomerForm.getInputByName("locality");
		vatInput.setValueAttribute(localityCodeJoseFaria);

		//o butão não tem name 
		HtmlInput insert = addCustomerForm.getInputByValue("Insert");
		HtmlPage reportPageAdress = null;
		try {
			reportPageAdress = insert.click();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("não deu submit");
		}

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
		assertTrue(reportPage.asXml().contains(nomeJoseFaria));

		int adressCount2 = 0; 

		if(report.asXml().contains("<table")) {
			List<HtmlElement> items = report.getByXPath("//tr");
			adressCount2 = items.size()-1;
		}

		System.out.println("adressCount2 = "+adressCount2);
		assertEquals(adressCount2, adressCount+1);
	}

	@Test
	public void InsertExistingCustomerAndCheckIfReturnsError() {

		assertEquals("WebAppDemo Menu", page.getTitleText());
		HtmlAnchor listingAllCustomers = page.getAnchorByHref("GetAllCustomersPageController");
		HtmlPage listingAllCustomersPage = null;
		try {
			listingAllCustomersPage = (HtmlPage) listingAllCustomers.openLinkInNewWindow();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		assertEquals("Customers Info", listingAllCustomersPage.getTitleText());

		if(listingAllCustomersPage.asXml().contains("<table")) {
			//obter o nome do 1º elemento
			String nome = listingAllCustomersPage.getByXPath("//tr[2]/td[1]/text()").get(0).toString();
		}


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
		vatInput.setValueAttribute(vatJoseFaria);
		HtmlInput designationInput = insertCustomerForm.getInputByName("designation");
		vatInput.setValueAttribute(nomeJoseFaria);
		HtmlInput phoneInput = insertCustomerForm.getInputByName("phone");
		vatInput.setValueAttribute(telJoseFaria);


		HtmlInput getCustomer = insertCustomerForm.getInputByName("submit");
		try {
			HtmlPage getCustomerSubmit = getCustomer.click();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

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
			requestSettings.getRequestParameters().add(new NameValuePair("vat", vatJoseFaria));
			requestSettings.getRequestParameters().add(new NameValuePair("designation", nomeJoseFaria));
			requestSettings.getRequestParameters().add(new NameValuePair("phone", telJoseFaria));
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
			System.out.println(mensagemErro);
		}
		assertTrue(mensagemErro.contains("It was not possible to fulfill the request: Can't add customer with vat number "+vatJoseFaria+"."));

		//o erro vai para o controller AddCustomerPageController

	}








}
