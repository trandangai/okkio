package io.okkio.services;

import io.okkio.domain.Blogs;
import io.okkio.domain.Careers;
import io.okkio.domain.CustomerSupports;
import io.okkio.domain.PartnerShip;
import io.okkio.dto.BlogDto;
import io.okkio.dto.CareerDto;
import io.okkio.dto.CustomerSupportDto;
import io.okkio.dto.PartnerShipDto;

import java.util.List;

public interface DiscoveryService {
    List<CareerDto> getAllCareers();

    List<BlogDto> getAllBlogs();

    List<PartnerShipDto> getAllPartnerShip();

    List<CustomerSupportDto> getAllCustomerSupports();

    CareerDto addNewCareer(CareerDto dto) throws Exception;

    BlogDto addNewBlog(BlogDto dto) throws Exception;

    PartnerShipDto addNewPartnerShip(PartnerShipDto dto) throws Exception;

    CustomerSupportDto addNewCustomerSupport(CustomerSupportDto dto) throws Exception;

    Careers getCareerById(Long id);

    Blogs getBlogById(Long id);

    PartnerShip getPartnerShipById(Long id);

    CustomerSupports getCustomerSupportsById(Long id);

    CareerDto updateCareer(CareerDto dto);

    BlogDto updateBlog(BlogDto dto);

    PartnerShipDto updatePartnerShip(PartnerShipDto dto);

    CustomerSupportDto updateCustomerSupports(CustomerSupportDto dto);
}
