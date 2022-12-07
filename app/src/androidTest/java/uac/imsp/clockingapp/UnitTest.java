package uac.imsp.clockingapp;

import static org.junit.Assert.assertNotNull;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import uac.imsp.clockingapp.Models.dao.EmployeeManager;

@RunWith(AndroidJUnit4.class)
public class UnitTest {

	private final Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

	public UnitTest(){
		assertNotNull(appContext);

	}
	@Test
	public void test(){
		new UnitTest();
		EmployeeManager employeeManager=new EmployeeManager(appContext);
		employeeManager.open();
		int i=0;

	}
}