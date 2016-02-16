# ngrinder-csv-analysis
解析ngrinder csv结果，统计TPS标准差，TPS波动率，最小/大RT，RT 25/50/75/80/85/90/95/99百分位数


1.将ngrinder 生成的csv文件：output.csv，放到工程src/main/resources下

2.执行src/main/java下ParseCsv.java文件

Console输出结果：

TPS平均值：257.88

TPS标准差：33.10

TPS波动率：12.84%

RT平均响应时间：19.43 ms

Min RT：14.90 ms

RT 25百分位数：18.07 ms

RT 50百分位数：19.14 ms

RT 75百分位数：20.33 ms

RT 80百分位数：20.78 ms

RT 85百分位数：21.29 ms

RT 90百分位数：21.86 ms

RT 95百分位数：23.52 ms

RT 99百分位数：25.91 ms

Max RT：46.93 ms
