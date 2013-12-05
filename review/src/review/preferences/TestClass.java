package review.preferences;

import java.io.File;
import java.rmi.server.LoaderHandler;
import java.util.Properties;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import review.utils.Common;

public class TestClass {
	public static final String constant1 = "somestring1";
	public static final String constant2 = "somestring2";
	public static final String constant3 = "somestring3";
	
	private Preferences preferences = ConfigurationScope.INSTANCE
			.getNode("review.basicPreferences");

	public String[] ReadPreferences() throws BackingStoreException {
		Preferences sub1 = preferences.node("Users");
		return sub1.keys();
	}

	public String GetPassword(String user) {
		Preferences sub1 = preferences.node("Users");
		return sub1.get(user, null);
	}

	public static String currentUser = "";
	//File with the name properties.xml and xml structure should exist in a folder Data
	public void LoadPreferances() {
		File file = new File(Common.getCodeReviewPath() + "\\properties.xml");
		Properties symbolMap = new Properties();
		try {
			// Populate the symbol map from the XML file
			symbolMap.loadFromXML(file.toURI().toURL().openStream());
		} catch (Exception e) {
		}
		Preferences sub1 = preferences.node("Users");
		for (Object j : symbolMap.keySet()) {
			sub1.put(j.toString(), symbolMap.getProperty(j.toString()));

		}
		try {
			preferences.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
