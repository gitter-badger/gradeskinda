package com.rothanak.gradeskinda.data.auth;

import com.rothanak.gradeskinda.domain.model.Credentials;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import org.junit.Test;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;

import rx.Observable;

public class RemoteLoginServiceTest {

    @Test
    public void loginSuccessful_ReturnsAuthToken() {
        // TODO replace manual test DI with Dagger
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient()
                .setCookieHandler(cookieManager);
        List<Interceptor> interceptors = client.interceptors();
        interceptors.add(logging);
        interceptors.add(chain -> {
            Request request = chain.request();
            Request.Builder requestBuilder = request.newBuilder();
            Observable.from(cookieManager.getCookieStore().getCookies()).subscribe(cookie ->
                    requestBuilder.addHeader("Cookie", cookie.getName() + "=" + cookie.getValue())
            );
            return chain.proceed(requestBuilder.build());
        });
        RemoteLoginService loginService = new RemoteLoginService(client, cookieManager);

        Credentials credentials = new Credentials("Username", "Password");
        AuthToken token = loginService.login(credentials).toBlocking().first();

        // todo
    }

}