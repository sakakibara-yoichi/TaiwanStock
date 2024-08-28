package com.taiwanstock.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBUI;
import com.taiwanstock.mainWindowFactory;
import com.taiwanstock.services.StockService;
import com.taiwanstock.dto.SearchResponseDTO;
import com.taiwanstock.dto.StockResponseDTO;
import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EquoteComponent {
    private final JPanel equoteBlock;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final JTextField inputField; // 輸入欄位
    private final JLabel stockName; // 股票名稱標籤
    private final JLabel changePercent; // 漲跌幅標籤
    private final JLabel currentPrice; // 當前價格標籤
    private final JLabel high; // 最高價標籤
    private final JLabel low; // 最低價標籤
    private final JLabel open; // 開盤價標籤
    private final JLabel previousClose; // 昨收價標籤
    private final JLabel errMsg; // 錯誤訊息標籤
    private final JPopupMenu searchResult; // 搜尋結果彈出下拉選單
    public StockResponseDTO stock; // 股票回應資料
    public SearchResponseDTO searchResponse; // 搜尋回應資料
    Map<String, String> label; // 標籤:代碼

    public EquoteComponent() {
        equoteBlock = new JPanel(new GridBagLayout());
//        equoteBlock.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = JBUI.insets(5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.SOUTH;

        // 第一排
        JPanel panel1nd = new JPanel();
        panel1nd.setLayout(new BoxLayout(panel1nd, BoxLayout.X_AXIS));
        JLabel stockCode = new JLabel("代碼：");
        inputField = new JTextField();
        JButton searchBtn = new JButton("搜尋");
        searchResult = new JPopupMenu();
        gbc.gridx = 0;
        gbc.gridy = 0;
        stockCode.setPreferredSize(new Dimension(40, 30));
        stockCode.setMaximumSize(new Dimension(40, 30));
        panel1nd.add(stockCode, gbc);
        gbc.gridx = 1;
        inputField.setPreferredSize(new Dimension(160, 30));
        inputField.setMaximumSize(new Dimension(160, 30));
        panel1nd.add(inputField, gbc);
        searchBtn.setPreferredSize(new Dimension(80, 30));
        searchBtn.setMaximumSize(new Dimension(80, 30));
        gbc.gridx = 2;
        panel1nd.add(searchBtn, gbc);
        gbc.gridx = 3;
        panel1nd.add(searchResult, gbc);

        gbc.gridx = 0;
        gbc.weightx = 0.1;
        equoteBlock.add(panel1nd, gbc);

        // 第二排
        JPanel panel2nd = new JPanel();
        panel2nd.setLayout(new BoxLayout(panel2nd, BoxLayout.X_AXIS));
        stockName = new JLabel("名稱：");
        stockName.setPreferredSize(new Dimension(300, 30));
        stockName.setMaximumSize(new Dimension(300, 30));
        panel2nd.add(stockName);
        gbc.gridy = 1;
        equoteBlock.add(panel2nd, gbc);

        // 第三排
        JPanel panel3nd = new JPanel();
        panel3nd.setLayout(new BoxLayout(panel3nd, BoxLayout.X_AXIS));
        currentPrice = new JLabel("當前價格：");
        currentPrice.setPreferredSize(new Dimension(150, 30));
        currentPrice.setMaximumSize(new Dimension(150, 30));
        changePercent = new JLabel("漲跌幅：");
        changePercent.setPreferredSize(new Dimension(200, 30));
        changePercent.setMaximumSize(new Dimension(200, 30));
        panel3nd.add(currentPrice);
        panel3nd.add(changePercent);
        gbc.gridy = 2;
        equoteBlock.add(panel3nd, gbc);

        // 第四排
        JPanel panel4nd = new JPanel();
        panel4nd.setLayout(new BoxLayout(panel4nd, BoxLayout.X_AXIS));
        previousClose = new JLabel("昨收：");
        previousClose.setPreferredSize(new Dimension(80, 30));
        previousClose.setMaximumSize(new Dimension(80, 30));
        open = new JLabel("開盤：");
        open.setPreferredSize(new Dimension(80, 30));
        open.setMaximumSize(new Dimension(80, 30));
        high = new JLabel("最高：");
        high.setPreferredSize(new Dimension(80, 30));
        high.setMaximumSize(new Dimension(80, 30));
        low = new JLabel("最低：");
        low.setPreferredSize(new Dimension(80, 30));
        low.setMaximumSize(new Dimension(80, 30));
        panel4nd.add(previousClose);
        panel4nd.add(open);
        panel4nd.add(high);
        panel4nd.add(low);
        gbc.gridy = 3;
        equoteBlock.add(panel4nd, gbc);

        // 第六排
        JPanel panel6nd = new JPanel();
        panel6nd.setLayout(new BoxLayout(panel6nd, BoxLayout.X_AXIS));
        errMsg = new JLabel("");
        errMsg.setPreferredSize(new Dimension(300, 30));
        errMsg.setMaximumSize(new Dimension(300, 30));
        errMsg.setForeground(JBColor.RED);

        panel6nd.add(errMsg);
        gbc.gridy = 6;
        equoteBlock.add(panel6nd, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.9;
        gbc.weighty = 0.3;
        equoteBlock.add(Box.createVerticalGlue(), gbc);

        searchBtn.addActionListener(e -> StockService.getSearchResult().fundQuoteName(
                "TW",
                inputField.getText(),
                50
        ).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NotNull Call<SearchResponseDTO> call, @NotNull Response<SearchResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    searchResponse = response.body();
                    label = searchResponse.data.items
                            .stream()
                            .collect(Collectors.toMap(
                                    item -> "<html>" + item.chName + " " + item.symbol.split(":")[1] + "</html>",
                                    item -> item.symbol.split(":")[1]
                            ));
                    showSuggestions(label.keySet().toArray(new String[0]));
                } else {
                    clearData();
                    errMsg.setText("錯誤訊息：無法取得資料");
                }
            }

            @Override
            public void onFailure(@NotNull Call<SearchResponseDTO> call, @NotNull Throwable t) {
                errMsg.setText("錯誤訊息： " + t.getMessage());
            }
        }));
    }
    // 顯示搜尋建議結果
    private void showSuggestions(String[] suggestions) {
        searchResult.removeAll();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (String suggestion : suggestions) {
            JMenuItem menuItem = new JMenuItem(suggestion);
            menuItem.setPreferredSize(new Dimension(inputField.getWidth(), 30));
            menuItem.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    menuItem.setBackground(JBColor.LIGHT_GRAY);
                    menuItem.setForeground(JBColor.BLACK);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    menuItem.setBackground(UIManager.getColor("MenuItem.background"));
                    menuItem.setForeground(UIManager.getColor("MenuItem.foreground"));
                }
            });
            menuItem.revalidate();
            menuItem.repaint();
            menuItem.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    searchResult.setVisible(true);
                }
            });
            menuItem.addActionListener(e -> {
                String selectedStockCode = label.get(e.getActionCommand());
                searchResult.setVisible(false);
                StockService.getStockInfo().getStock(
                        selectedStockCode.contains(".TW") ? selectedStockCode : selectedStockCode + ".TW",
                        System.currentTimeMillis(),
                        "tw",
                        "zh-Hant-TW",
                        "none",
                        "2h3pnulg7tklc",
                        "TW",
                        "finance",
                        "Asia/Taipei",
                        "1.2.902",
                        true
                ).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NotNull Call<StockResponseDTO> call, @NotNull Response<StockResponseDTO> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            stock = response.body();
                            updateLabels();
                            errMsg.setText("");
                            changeLabelColor();
                        } else {
                            clearData();
                            errMsg.setText("錯誤訊息：無法取得資料");
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<StockResponseDTO> call, @NotNull Throwable t) {
                        clearData();
                        errMsg.setText("錯誤訊息： " + t.getMessage());
                    }
                });
            });
            panel.add(menuItem);
            panel.add(Box.createRigidArea(new Dimension(0, 5)));
            panel.add(new JSeparator(SwingConstants.HORIZONTAL));
        }
        JBScrollPane scrollPane = new JBScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(200, 100));
        searchResult.add(scrollPane);
        searchResult.show(inputField, 0, inputField.getHeight());
        searchResult.setVisible(true);
    }
    // 更新標籤資料
    private void updateLabels() {
        stockName.setText("名稱：" + stock.data.get(0).chart.meta.symbol + " " + stock.data.get(0).chart.meta.name);
        currentPrice.setText("當前價格：" + stock.data.get(0).chart.quote.price);
        mainWindowFactory.statusBar.setInfo("最後更新時間：" + sdf.format(new Date()));
        high.setText("最高：" + stock.data.get(0).chart.quote.dayHighPrice);
        low.setText("最低：" + stock.data.get(0).chart.quote.dayLowPrice);
        open.setText("開盤：" + stock.data.get(0).chart.quote.openPrice);
        previousClose.setText("昨收：" + stock.data.get(0).chart.quote.previousClose);
        changePercent.setText("漲跌幅：" + stock.data.get(0).chart.quote.changePercent + "% / " + stock.data.get(0).chart.quote.change);
    }
    // 取得顏色色碼
    private void changeLabelColor() {
        Color color = JBColor.WHITE;
        float changeValue = Float.parseFloat(stock.data.get(0).chart.quote.change);
        if (changeValue > 0) {
            color = Color.decode("#FF6F61");
        } else if (changeValue < 0) {
            color = JBColor.GREEN;
        }
        setLabelColors(color);
    }
    // 設定標籤顏色
    private void setLabelColors(Color color) {
        stockName.setForeground(color);
        currentPrice.setForeground(color);
        high.setForeground(color);
        low.setForeground(color);
        open.setForeground(color);
        changePercent.setForeground(color);
        previousClose.setForeground(color);
    }

    private void clearData() {
        stockName.setText("名稱：");
        currentPrice.setText("當前價格：");
        high.setText("最高：");
        low.setText("最低：");
        open.setText("開盤：");
        previousClose.setText("昨收：");
        changePercent.setText("漲跌幅：");
    }

    public JPanel getContent() {
        return equoteBlock;
    }
}
