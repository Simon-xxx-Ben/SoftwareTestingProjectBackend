### 刷新章节掌握度

`@PutMapping("refresh-chapter-degree")`

输入参数：无

输出参数：`List<Double>.toString`

### 推荐某个id相关的题目列表

`@GetMapping("wrong-questions/ai/{id}")`

输入参数：`(@PathVariable int id)`

输出参数：

1. 未得到AI返回信息：`[]`，`空List<INteger>.toString`
2. 得到AI返回信息但未匹配中：`[]`，`空List<INteger>.toString`
3. 得到AI返回信息但`List`中存在非`Integer`数字：`[]`，`空List<INteger>.toString`
4. 匹配中：`[1, 2, 3, ...]`，如`[1, 2, 3]`，`List<INteger>.toString`

### 能力成长曲线

`@GetMapping("chart/growing-capacity")`

输入参数：无

输出参数：

1. 未得到AI返回信息：空字符串
2. 得到AI返回信息但未匹配中：空字符串
3. 匹配中：以`https://mdn.alipayobjects.com/`开头的字符串

### 章节掌握度

`@GetMapping("chart/mastery-degree")`

输入参数：无

输出参数：

1. 未得到AI返回信息：空字符串
2. 得到AI返回信息但未匹配中：空字符串
3. 匹配中：以`https://mdn.alipayobjects.com/`开头的字符串