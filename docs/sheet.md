# 前情提要 #

名詞均以 Google Sheet 的 zh-tw 語系為準。

假設有一個合併儲存格範圍是 C4～E5，
程式在抓取時，只有 C4（左上角）會有值，
其餘 C5 / D4 / D5 / E4 / E5 都不會有值。

程式無法抓取任何樣式，
反之，無論什麼設什麼樣式都不會影響程式運作
（包含隱藏欄/列、凍結欄/列）。


# 必要條件 #

工作表的第一列必須是欄位名稱，如下表：

| 名稱       | 限制條件 (`RealEstate.validator`) 
|------------|-----------------------------------
| 下架       | 合法日期（空白代表未下架）
| 區         | 不可空白
| 地段       | 不可空白
| 小段       |
| 地號       | 不可空白
| 土地面積   | 不可空白、合法數字
| 權利分子   |
| 權利分母   | 不可為 0
| 使用分區   |
| 使用地類別 |
| 公告現值   | 不可空白、合法數字
| 重劃       |
| 臨路       |
| 售價       | 不可空白、合法數字
| 開發人員   |
| 備註       |

+ 欄位名稱 **不能** 改變
+ 欄位順序可以改變
+ 可增加其他欄位，但程式不會處理

第二列以後均視為資料，一列為一筆。
若對應欄位未滿足上述 **所有** 限制條件，則程式會忽略該列不處理。

若有多筆資料有相同區、地段、地號（目前沒有包含小段），
則「下架」日期必須不同（空白 = 空白、空白 != 任何合法日期）。