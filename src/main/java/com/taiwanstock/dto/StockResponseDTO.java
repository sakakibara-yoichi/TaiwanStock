package com.taiwanstock.dto;
import java.util.List;

public class StockResponseDTO {
    public List<Data> data;

    public static class Data {
        public String symbol;
        public Chart chart;

        // Getters and Setters

        public static class Chart {
            public Meta meta;
            public List<Long> timestamp;
            public Indicators indicators;
            public Quote quote;

            public static class Meta {
                public String currency;
                public String symbol;
                public String name;
                public String exchangeName;
                public String instrumentType;
                public Long firstTradeDate;
                public Long regularMarketTime;
                public Long gmtoffset;
                public String timezone;
                public String exchangeTimezoneName;
                public Double regularMarketPrice;
                public Double chartPreviousClose;
                public Double previousClose;
                public Double limitUpPrice;
                public Double limitDownPrice;
                public int scale;
                public int priceHint;
                public TradingPeriod currentTradingPeriod;
                public List<List<TradingPeriod>> tradingPeriods;
                public String dataGranularity;
                public String range;
                public List<String> validRanges;

                // Getters and Setters

                public static class TradingPeriod {
                    public String timezone;
                    public Long start;
                    public Long end;
                    public Long gmtoffset;

                    // Getters and Setters
                }
            }

            public static class Indicators {
                public List<Quote> quote;

                // Getters and Setters

                public static class Quote {
                    public List<Double> close;
                    public List<Double> high;
                    public List<Double> low;
                    public List<Double> open;
                    public List<Long> volume;

                    // Getters and Setters
                }
            }

            public static class Quote {
                public String price;
                public String bid;
                public String ask;
                public String openPrice;
                public String previousClose;
                public String dayHighPrice;
                public String dayLowPrice;
                public String change;
                public String changePercent;
                public String volume;
                public String previousVolume;
                public boolean limitUp;
                public boolean limitDown;
                public String limitUpPrice;
                public String limitDownPrice;
                public String refreshedTs;
                public String marketStatus;
                public String turnoverM;
                public String avgPrice;
                public String inMarket;
                public String outMarket;
            }
        }
    }
}
