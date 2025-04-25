package com.loris.challenge.api.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.loris.challenge.api.models.entity.Path;
import com.loris.challenge.api.models.entity.Station;

public class ApiServiceImplTest {
    private static List<Path> paths = new ArrayList<>();
	private static Station station10 = new Station();
	private static Station station11 = new Station();
	private static Station station12 = new Station();
	private static Station station13 = new Station();

    @BeforeAll
    static void setUp() {        
		station10.setId(10l);		
		station11.setId(11l);		
		station12.setId(12l);		
		station13.setId(13l);
	
		Path path1 = new Path();
		path1.setId(1L);
		path1.setStationSource(station10);
		path1.setStationDestination(station11);
		path1.setCost(50d);

		Path path2 = new Path();
		path2.setId(2L);
		path2.setStationSource(station10);
		path2.setStationDestination(station12);
		path2.setCost(100d);

		Path path3 = new Path();
		path3.setId(3L);
		path3.setStationSource(station10);
		path3.setStationDestination(station13);
		path3.setCost(60d);

		Path path4 = new Path();
		path4.setId(4L);
		path4.setStationSource(station13);
		path4.setStationDestination(station12);
		path4.setCost(20d);

		paths.add(path1);
		paths.add(path2);
		paths.add(path3);
		paths.add(path4);
    }   

    @Test
    void testGetShortestPathOne() {        		
        ApiServiceImpl apiService = new ApiServiceImpl();
        Map<String, Object> pathsResult = apiService.findOptimalPath(paths, station10, station13);
        Map<String, Object> expectedPaths = new HashMap<>();
		
		String expectedPath = "[10, 13]";
		
		expectedPaths.put("path", expectedPath);
		expectedPaths.put("cost" ,"60.0");

        assertEquals(expectedPaths.toString(), pathsResult.toString());
    }

    @Test
    void testGetShortestPathTwo() {        		
        ApiServiceImpl apiService = new ApiServiceImpl();
        Map<String, Object> pathsResult = apiService.findOptimalPath(paths, station12, station11);
        Map<String, Object> expectedPaths = new HashMap<>();
        String expectedPath = "[12, 13, 10, 11]";
		
		expectedPaths.put("path", expectedPath);
		expectedPaths.put("cost" ,"130.0");
				
        assertEquals(expectedPaths.toString(), pathsResult.toString());
    }
}
