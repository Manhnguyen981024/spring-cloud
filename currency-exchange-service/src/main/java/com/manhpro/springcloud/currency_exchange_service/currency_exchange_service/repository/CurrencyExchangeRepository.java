package com.manhpro.springcloud.currency_exchange_service.currency_exchange_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manhpro.springcloud.currency_exchange_service.currency_exchange_service.bean.CurrencyExchangeBean;

@Repository
public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchangeBean, Long>{
	CurrencyExchangeBean findByFromAndTo(String from, String to);
}
