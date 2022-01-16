package com.Micro.Services.reviewservice.facade;

import java.util.HashMap;

import com.Micro.Services.reviewservice.exceptions.EntityNotFoundException;

public interface ProductFacade {

	public HashMap getProduct(String productId) throws EntityNotFoundException;
}
