package com.forage.action;

import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.forage.bean.ReviewBean;
import com.forage.dao.ReviewDAO;
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
	@Path("/vendor")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String getVendorReview(@QueryParam("vendor") BigDecimal vendorId){
		ReviewDAO reviewDAO = new ReviewDAO();
		List<ReviewBean> reviewList = reviewDAO.getVendorReviews(vendorId);
		if(reviewList == null || reviewList.size() == 0){
			throw new NotFoundException("getVendorReview", "Vendor <"+vendorId+"> has no reviews.");
		}
		return ReviewJSON.constructList(reviewList);
	}
	
	
	@GET
	@Path("/bycustomer")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String getReviewByCustomer(@QueryParam("customer") BigDecimal customerId){
		ReviewDAO reviewDAO = new ReviewDAO();
		List<ReviewBean> reviewList = reviewDAO.getReviewsWrittenByCustomer(customerId);
		if(reviewList == null || reviewList.size() == 0){
			throw new NotFoundException("getReviewByCustomer", "Customer <"+customerId+"> has not written any reviews.");
		}
		return ReviewJSON.constructList(reviewList);
	}
	
	@GET
	@Path("/enable")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String enableReview(@QueryParam("reviewId") BigDecimal reviewId, @QueryParam("userId") BigDecimal userId){
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
	@Path("/disable")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String disableReview(@QueryParam("reviewId") BigDecimal reviewId, @QueryParam("userId") BigDecimal userId){
		ReviewDAO reviewDAO = new ReviewDAO();
		reviewDAO.enableReview(reviewId, false);
		ReviewBean review = reviewDAO.getReview(reviewId);
		reviewDAO.updateLastModified(review, userId);
		if(review == null || review.getReviewId() == null){
			throw new NotFoundException("disableReview", "Review <"+reviewId+"> not exists.");
		}
		return ReviewJSON.construct(review);
	}
	
	
	@POST
	@Path("/update")  
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String updateReview(ReviewBean review){
		ReviewDAO reviewDAO = new ReviewDAO();
		reviewDAO.updateReview(review);
		if(review == null || review.getReviewId() == null){
			throw new NotFoundException("updateReview", "Review not exists.");
		}
		return ReviewJSON.construct(review);
	}
	
	@POST
	@Path("/create")  
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String createReview(ReviewBean review){
		ReviewDAO reviewDAO = new ReviewDAO();
		reviewDAO.createReview(review);
		if(review == null || review.getReviewId() == null){
			throw new GoneException("createReview", "Review <" + review.getRemarks() +"> not created.");
		}
		return ReviewJSON.construct(review);
	}
	
	@GET
	@Path("/approve")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String approveReview(@QueryParam("reviewId") BigDecimal reviewId, @QueryParam("userId") BigDecimal userId){
		ReviewDAO reviewDAO = new ReviewDAO();
		reviewDAO.approveReview(reviewId, true);
		ReviewBean review = reviewDAO.getReview(reviewId);
		reviewDAO.updateLastModified(review, userId);
		if(review == null || review.getReviewId() == null){
			throw new NotFoundException("approveReview", "Review <"+reviewId+"> not exists.");
		}
		return ReviewJSON.construct(review);
	}
	
}
