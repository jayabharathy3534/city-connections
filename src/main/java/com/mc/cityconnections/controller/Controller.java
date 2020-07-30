package com.mc.cityconnections.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mc.cityconnections.service.Service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "City Connections", description = "Find if two cities are connected")
public class Controller {

	@Autowired
	private Service service;
	 @ApiOperation(value = "Find if two cities are connected",
	            notes = "Returns yes if cites connected and no otherwise ",
	            response = String.class)
	    @ApiResponses({
	            @ApiResponse(code = 400, message = "Either destination or origin city does not exist or invalid", response = NullPointerException.class),
	            @ApiResponse(code = 500, message = "Generic error", response = Exception.class)
	    })
	@GetMapping("/connected")
	public String isConnected(
            @ApiParam(name = "origin", value = "Origin City name", required = true) @RequestParam String origin,
            @ApiParam(name = "destination", value = "Destination City name", required = true) @RequestParam String destination) {
		return service.isConnected(origin, destination);
	}

}
