package com.taiwanstock.dto;
import java.util.List;

public class SearchResponseDTO {
    public int statusCode;
    public String message;
    public Response data;
    // Getters and Setters
    public class Response {
        public List<ResItem> items;
        public int total;
        public int perPage;
        public int currentPage;
        public int lastPage;
        public int from;
        public int to;
        public String prevPageUrl;
        public String nextPageUrl;
            public class ResItem {
                public String objectType;
                public String market;
                public String code;
                public String type;
                public String mtype;
                public String symbol;
                public String chName;
                public String enName;
                public String chNameFull;
                public String enNameFull;
                public String exchange;
                public String industry;

            }
        }
}
