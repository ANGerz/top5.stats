package top5.rocket.stats.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import top5.rocket.stats.DAO.PlayerDAO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private PlayerDAO playerDao;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws IOException 
	 * @throws JSONException 
	 */
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) throws JSONException, IOException {
		logger.info("Welcome home! The client locale is {}.", locale);
		playerDao.createPlayer("Gero", "[U:1:62587760]");
		playerDao.createPlayer("Mooshi", "[U:1:55570032]");
		
		playerDao.fetchListLogs(playerDao.get("mooshi"));
		//playerDao.fetchPlayerStatsFromLogsTF("[U:1:62587760]");
		
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	public static JSONObject readJsonFromUrl(String url) throws JSONException, IOException{
		InputStream is = new URL(url).openStream();
		try{
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonTxt = readAll(rd);
			JSONObject json = new JSONObject(jsonTxt);
			return json;
		}finally{
			is.close();
		}
	}
	
	public static String readAll(Reader rd) throws IOException{
		StringBuilder sb = new StringBuilder();
		int cp;
		while((cp = rd.read())!=-1){
			sb.append((char)cp);
		}
		return sb.toString();
	}
	
}
