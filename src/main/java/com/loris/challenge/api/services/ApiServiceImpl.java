package com.loris.challenge.api.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loris.challenge.api.models.dto.ShortestPathDTO;
import com.loris.challenge.api.models.entity.Path;
import com.loris.challenge.api.models.entity.Station;
import com.loris.challenge.api.repositories.PathRepository;
import com.loris.challenge.api.repositories.StationRepository;

import lombok.Data;

@Service
public class ApiServiceImpl implements ApiService {
	@Autowired
	private PathRepository pathRepository;

	@Autowired
	private StationRepository stationRepository;

	@SuppressWarnings("unchecked")
	@Override
	public ShortestPathDTO getShortestPath(Long sourceId, Long destinationId) {
		List<Path> paths = pathRepository.findAll();
		Station sourceStation = stationRepository.findById(sourceId)
				.orElseThrow(() -> new IllegalArgumentException("Source station not found"));
		Station destinationStation = stationRepository.findById(destinationId)
				.orElseThrow(() -> new IllegalArgumentException("Destination station not found"));
				
		Map<String, Object> result = findOptimalPath(paths, sourceStation, destinationStation);
		
		ShortestPathDTO shortestPathDTO = new ShortestPathDTO();
		shortestPathDTO.setPath((List<Long>) result.get("path"));
		shortestPathDTO.setCost((Double) result.get("cost"));
		
		return shortestPathDTO;				
	}

	public Map<String, Object> findOptimalPath(List<Path> paths, Station source, Station destination) {
		// Build the graph from the paths list
		Map<Station, List<Path>> graph = new HashMap<>();
		for (Path path : paths) {
			graph.putIfAbsent(path.getStationSource(), new ArrayList<>());
			graph.putIfAbsent(path.getStationDestination(), new ArrayList<>());
			graph.get(path.getStationSource()).add(path);

			// Since the paths are bidirectional
			Path reversePath = new Path();
			reversePath.setId(path.getId());
			reversePath.setStationSource(path.getStationDestination());
			reversePath.setStationDestination(path.getStationSource());
			reversePath.setCost(path.getCost());
			graph.get(path.getStationDestination()).add(reversePath);
		}

		// Initialize variables for Dijkstra's algorithm
		Map<Station, Double> costs = new HashMap<>();
		Map<Station, Station> previous = new HashMap<>();
		PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(Node::getCost));

		// Initialization
		for (Station station : graph.keySet()) {
			costs.put(station, Double.MAX_VALUE);
		}
		costs.put(source, 0.0);
		priorityQueue.add(new Node(source, 0.0));

		// Process the graph
		while (!priorityQueue.isEmpty()) {
			Node current = priorityQueue.poll();

			// Stop if the destination is reached
			if (current.getStation().equals(destination))
				break;

			for (Path path : graph.get(current.getStation())) {
				Double newCost = current.getCost() + path.getCost();
				if (newCost < costs.get(path.getStationDestination())) {
					costs.put(path.getStationDestination(), newCost);
					previous.put(path.getStationDestination(), current.getStation());
					priorityQueue.add(new Node(path.getStationDestination(), newCost));
				}
			}
		}

		// Reconstruct the path
		List<Long> stationIds = new LinkedList<>();
		for (Station station = destination; station != null; station = previous.get(station)) {
			stationIds.add(0, station.getId()); // Insert at the beginning
		}

		// Verify if a valid path exists
		if (stationIds.isEmpty() || !stationIds.get(0).equals(source.getId())) {
			throw new IllegalStateException(
					"No path found between " + source.getName() + " and " + destination.getName());
		}

		// Build the response
		Map<String, Object> response = new HashMap<>();
		response.put("path", stationIds);
		response.put("cost", costs.get(destination));

		return response;
	}

	@Data
	private static class Node {
		private Station station;
		private Double cost;

		public Node(Station station, Double cost) {
			this.station = station;
			this.cost = cost;
		}
	}

}
