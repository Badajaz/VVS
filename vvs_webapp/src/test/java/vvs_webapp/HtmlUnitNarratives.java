package vvs_webapp;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class HtmlUnitNarratives {

	private static final String APPLICATION_URL = "http://localhost:8080/VVS_webappdemo/";


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
		String idJoseFaria = "1";
		String telJoseFaria = "914276732";
		String nomeJoseFaria = "JOSE FARIA";
		String vatJoseFaria = "197672337";

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
		System.out.println(textReportPage);
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
		
		
		
		
		
	}










}
