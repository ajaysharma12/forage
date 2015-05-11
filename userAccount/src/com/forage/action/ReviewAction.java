package com.forage.action;

import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.forage.bean.ReviewBean;
import com.forage.dao.ReviewDAO;
import com.forage.exception.BadRequestException;
import com.forage.exception.GoneException;
import com.forage.exception.NotFoundException;
import com.forage.json.ReviewJSON;

@Path("/review")
public class ReviewAction {

	@GET
	@Path("{id}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String getReview(@PathParam("id") BigDecimal reviewId){
		ReviewDAO reviewDAO = new ReviewDAO();
		ReviewBean review = reviewDAO.getReview(reviewId);
		if(review == null || review.getReviewId() == null){
			throw new NotFoundException("getReview", "Review <"+ reviewId + "> not found.");
		}
		return ReviewJSON.construct(review);
	}
	
	@GET
	@Path("/vendor/{id}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String getVendorReview(@PathParam("id") BigDecimal vendorId){
		ReviewDAO reviewDAO = new ReviewDAO();
		List<ReviewBean> reviewList = reviewDAO.getVendorReviews(vendorId);
		if(reviewList == null || reviewList.size() == 0){
			throw new NotFoundException("getVendorReview", "Vendor <"+vendorId+"> has no reviews.");
		}
		return ReviewJSON.constructList(reviewList);
	}
	
	
	@GET
	@Path("/fromcustomer/{id}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String getReviewByCustomer(@PathParam("id") BigDecimal customerId){
		ReviewDAO reviewDAO = new ReviewDAO();
		List<ReviewBean> reviewList = reviewDAO.getReviewsWrittenByCustomer(customerId);
		if(reviewList == null || reviewList.size() == 0){
			throw new NotFoundException("getReviewByCustomer", "Customer <"+customerId+"> has not written any reviews.");
		}
		return ReviewJSON.constructList(reviewList);
	}
	
		
	@POST
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String createReview(ReviewBean review){
		if(review.getCustomerId() == null){
			throw new BadRequestException("ReviewAction.createReview", "Please provide the customer Id");
		}
		if(review.getVendorId() == null){
			throw new BadRequestException("ReviewAction.createReview", "Please provide the Vendor Id");
		}
		ReviewDAO reviewDAO = new ReviewDAO();
		reviewDAO.createReview(review);
		if(review == null || review.getReviewId() == null){
			throw new GoneException("createReview", "Review <" + review.getRemarks() +"> not created.");
		}
		return ReviewJSON.construct(review);
	}
	
	@PUT  
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String updateReview(ReviewBean review){
		ReviewDAO reviewDAO = new ReviewDAO();
		ReviewBean checkReview = reviewDAO.getReview(review.getReviewId());
		if(checkReview == null || checkReview.getReviewId() == null){
			throw new NotFoundException("ReviewAction.updateReview", "Review <"+review.getReviewId()+"> do not exists.");
		}
		reviewDAO.updateReview(review);
		if(review == null || review.getReviewId() == null){
			throw new NotFoundException("updateReview", "Review not exists.");
		}
		return ReviewJSON.construct(review);
	}

	
	@PUT
	@Path("/approve/{reviewid}/{approver}/{onoff}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String approveReview(@PathParam("reviewid") BigDecimal reviewId, @PathParam("approver") BigDecimal userId, @PathParam("onoff") String onoff){
		ReviewDAO reviewDAO = new ReviewDAO();
		reviewDAO.approveReview(reviewId, true);
		ReviewBean review = reviewDAO.getReview(reviewId);
		if(review == null || review.getReviewId() == null){
			throw new NotFoundException("ReviewAction.updateReview", "Review <"+ reviewId +"> do not exists.");
		}
		if("ON".equals(onoff)){
			reviewDAO.approveReview(reviewId, true);
		}else if("OFF".equals(onoff)){
			reviewDAO.approveReview(reviewId, false);
		}
		reviewDAO.updateLastModified(review, userId);
		if(review == null || review.getReviewId() == null){
			throw new NotFoundException("approveReview", "Review <"+reviewId+"> not exists.");
		}
		return ReviewJSON.construct(review);
	}
	
	@PUT
	@Path("/enable/{reviewid}/{approver}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String enableReview(@PathParam("reviewid") BigDecimal reviewId, @PathParam("approver") BigDecimal userId){
		ReviewDAO reviewDAO = new ReviewDAO();
		ReviewBean review = reviewDAO.getReview(reviewId);
		review.setEnabledFlag("Y");
		reviewDAO.updateReview(review);
		reviewDAO.updateLastModified(review, userId);
		if(review == null || review.getReviewId() == null){
			throw new NotFoundException("enableReview", "Review <"+reviewId+"> not exists.");
		}
		return ReviewJSON.construct(review);
	}
	
	@GET
	@Path("/disable/{reviewid}/{approver}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String disableReview(@PathParam("reviewid") BigDecimal reviewId, @PathParam("approver") BigDecimal userId){
		ReviewDAO reviewDAO = new ReviewDAO();
		reviewDAO.enableReview(reviewId, false);
		ReviewBean review = reviewDAO.getReview(reviewId);
		reviewDAO.updateLastModified(review, userId);
		if(review == null || review.getReviewId() == null){
			throw new NotFoundException("disableReview", "Review <"+reviewId+"> not exists.");
		}
		return ReviewJSON.construct(review);
	}
	
}
