package com.taiwanstock.services;
import com.taiwanstock.dto.SearchResponseDTO;
import com.taiwanstock.dto.StockResponseDTO;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class StockService {
    private static final String YAHOO_URL = "https://tw.stock.yahoo.com/";
    private static final String ESS_URL = "https://ess.api.cnyes.com";
    public interface Api {
        @GET("/_td-stock/api/resource/FinanceChartService.ApacLibraCharts;autoRefresh={timestamp};symbols=[%22{symbol}%22];type=tick")
            Call<StockResponseDTO> getStock(
                @Path("symbol") String symbol,
                @Path("timestamp") long timestamp,
                @Query("intl") String intl,
                @Query("lang") String lang,
                @Query("partner") String partner,
                @Query("prid") String prid,
                @Query("region") String region,
                @Query("site") String site,
                @Query("tz") String tz,
                @Query("ver") String ver,
                @Query("returnMeta") boolean returnMeta
            );
        @GET("/ess/api/v1/siteSearch/fundQuoteName")
        Call<SearchResponseDTO> fundQuoteName(
                @Query("category") String category,
                @Query("q") String q,
                @Query("limit") int limit
        );
    }

    private static Api api;
    // 取得股票資料
    public static Api getStockInfo() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(YAHOO_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        api = retrofit.create(Api.class);
        return api;
    }
    // 取得搜尋結果
    public static Api getSearchResult() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ESS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
        return api;
    }
}