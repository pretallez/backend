package com.pretallez.common.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import com.pretallez.common.config.properties.PaymentProperties;
import com.pretallez.common.util.HeaderUtils;

import lombok.RequiredArgsConstructor;

/**
 * 도메인별 외부 API 연동을 위한 RestClient 설정 클래스
 */
@Configuration
@RequiredArgsConstructor
public class RestClientConfig {
	private final PaymentProperties paymentProperties;

	/**
	 * 결제 API 호출용 RestClient Bean을 생성한다.
	 *
	 * @return 결제 API용 RestClient 객체
	 */
	@Bean
	public RestClient paymentRestClient() {
		return buildClient(
			paymentProperties.getBaseUrl(),
			paymentProperties.getConnectTimeout(),
			paymentProperties.getReadTimeout(),
			HeaderUtils.basicAuthHeaders(paymentProperties.getSecretKey())
		);
	}

	/**
	 * 주어진 설정을 기반으로 RestClient 인스턴스를 생성한다.
	 *
	 * @param baseUrl			요청 기본 URL
	 * @param connectTimeout	연결 타임아웃(ms)
	 * @param readTimeout		읽기 타임아웃(ms)
	 * @param headers			요청 기본 헤더
	 * @return					구성된 RestClient 객체
	 */
	private RestClient buildClient(String baseUrl, int connectTimeout, int readTimeout, Map<String, String> headers) {
		RestClient.Builder builder = RestClient.builder()
			.baseUrl(baseUrl)
			.requestFactory(requestFactory(connectTimeout, readTimeout));

		if (headers != null && !headers.isEmpty()) {
				headers.forEach(builder::defaultHeader);
		}
		return builder.build();
	}

	/**
	 * 지정된 타임아웃을 기반으로 ClientHttpRequestFactory를 생성한다.
	 *
	 * @param connectTimeout	연결 타임아웃(ms)
	 * @param readTimeout		읽기 타임아웃(ms)
	 * @return					ClientHttpRequestFactory 객체
	 */
	private ClientHttpRequestFactory requestFactory(int connectTimeout, int readTimeout) {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(connectTimeout);
		factory.setReadTimeout(readTimeout);

		return factory;
	}
}
