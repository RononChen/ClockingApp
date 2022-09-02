package uac.imsp.clockingapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import uac.imsp.clockingapp.Models.entity.Day;

public class DayTest {
    @Test
    public void  test(){
        Day day=new Day("1970-01-01");
        assertNotNull(day);
        assertEquals("1970-01-01",day.getDate());
        assertEquals("1970",day.getFormatedYear());
        assertEquals("01",day.getFormatedMonth());
        assertEquals("01",day.getFormatedDay());
        assertEquals(1970,day.getYear());
        assertEquals(1,day.getDayOfMonth());
        assertEquals(1,day.getMonth());
        assertEquals("01/01/1970",day.getFrenchFormat());

    }
    @Test
    public void testDayWithCurrentDate(){
        Day now = new Day();
        assertNotNull(now);
        assertEquals("2022-09-02",now.getDate());
        assertEquals("2022",now.getFormatedYear());
        assertEquals("09",now.getFormatedMonth());
        assertEquals("02",now.getFormatedDay());
        assertEquals(2022,now.getYear());
        assertEquals(2,now.getDayOfMonth());
        assertEquals(9,now.getMonth());

    }
}


