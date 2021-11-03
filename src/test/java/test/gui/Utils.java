package test.gui;

import java.util.concurrent.TimeUnit;

import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JComboBoxFixture;
import org.assertj.swing.fixture.JPanelFixture;
import org.assertj.swing.fixture.JSpinnerFixture;

public class Utils {
public static void setDateCalendar(FrameFixture frame, int year, int month, int day) {
    	
    	JPanelFixture pfcalendar=frame.panel("calendar");
    	
    	
    	JPanelFixture monthFixture=pfcalendar.panel("JMonthChooser");
    	JComboBoxFixture comboMonthFixture=monthFixture.comboBox();

    	//JComboBoxFixture comboMonthFixture=pfcalendar.comboBox();

    	comboMonthFixture.selectItem(month);
    	
    	
    	JPanelFixture yearFixture=pfcalendar.panel("JYearChooser");
    	JSpinnerFixture yearSpinnerFixture=yearFixture.spinner();
    	yearSpinnerFixture.enterText(Integer.toString(year));
    	
    	
    	JPanelFixture dayFixture=pfcalendar.panel("JDayChooser");
    	/*JButtonFixture button = frame.button(new GenericTypeMatcher<JButton>(JButton.class) {
    		  @Override
    		  protected boolean isMatching(JButton button) {
    		    return (Integer.toString(day).equals(button.getText()) );
    		  }
    		});
    	*/
    	
    	JButtonFixture button=dayFixture.button(JButtonMatcher.withText(Integer.toString(day)));

    	button.click();
    }
public static void lo(int s) {
	try {
			TimeUnit.SECONDS.sleep(s);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
}
}
