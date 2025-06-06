-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: softtest
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `chapter`
--

DROP TABLE IF EXISTS `chapter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chapter` (
  `id` varchar(10) NOT NULL,
  `name` varchar(500) NOT NULL,
  `degree` double NOT NULL DEFAULT '0',
  `practice_hard_value` int DEFAULT '2',
  PRIMARY KEY (`id`),
  UNIQUE KEY `chapter_pk_2` (`id`),
  UNIQUE KEY `chapter_pk_3` (`name`),
  CONSTRAINT `chapter_chk_1` CHECK (((`degree` >= 0) and (`degree` <= 1)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chapter`
--

LOCK TABLES `chapter` WRITE;
/*!40000 ALTER TABLE `chapter` DISABLE KEYS */;
INSERT INTO `chapter` VALUES ('jf','积分',0,2),('jx','极限',0.41,1),('wf','微分',0,1);
/*!40000 ALTER TABLE `chapter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exam_question`
--

DROP TABLE IF EXISTS `exam_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exam_question` (
  `id` int NOT NULL AUTO_INCREMENT,
  `exam_id` int NOT NULL,
  `question_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `exam_question_pk_2` (`id`),
  KEY `exam_question_exams_id_fk` (`exam_id`),
  KEY `exam_question_questions_id_fk` (`question_id`),
  CONSTRAINT `exam_question_exams_id_fk` FOREIGN KEY (`exam_id`) REFERENCES `exams` (`id`),
  CONSTRAINT `exam_question_questions_id_fk` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exam_question`
--

LOCK TABLES `exam_question` WRITE;
/*!40000 ALTER TABLE `exam_question` DISABLE KEYS */;
INSERT INTO `exam_question` VALUES (1,1,2),(2,1,3),(3,3,4),(4,3,5),(5,3,6);
/*!40000 ALTER TABLE `exam_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exams`
--

DROP TABLE IF EXISTS `exams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exams` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(1000) NOT NULL,
  `start_time` date NOT NULL,
  `end_Time` date DEFAULT NULL,
  `duration` int NOT NULL,
  `complete_time` date DEFAULT NULL,
  `score` double DEFAULT NULL,
  `is_completed` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `exam_pk_2` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exams`
--

LOCK TABLES `exams` WRITE;
/*!40000 ALTER TABLE `exams` DISABLE KEYS */;
INSERT INTO `exams` VALUES (1,'1','2025-06-04','2025-06-05',120,'2025-06-04',90,1),(3,'1','2025-06-06','2025-06-07',120,NULL,0,1),(4,'1','2025-06-02','2025-06-03',120,'2025-06-04',100,1);
/*!40000 ALTER TABLE `exams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `chapter_id` varchar(10) NOT NULL,
  `content` text NOT NULL,
  `explanation` text NOT NULL,
  `answer` text,
  `score_value` double DEFAULT NULL,
  `hard_value` int NOT NULL,
  `my_answer` text,
  `correct_count` int DEFAULT '0',
  `incorrect_count` int DEFAULT '0',
  `is_wrong` tinyint(1) DEFAULT '0',
  `question_type` int NOT NULL DEFAULT '3',
  PRIMARY KEY (`id`),
  UNIQUE KEY `questions_pk` (`id`),
  KEY `questions_chapter_id_fk` (`chapter_id`),
  CONSTRAINT `questions_chapter_id_fk` FOREIGN KEY (`chapter_id`) REFERENCES `chapter` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (2,'jx','\\lim_{x \\to \\infty} f(x, y)','设  n  是正整数，记  S_n  为曲线  \n\ny = \\mathrm{e}^{-x} \\sin x \\quad (0 \\leqslant x \\leqslant n\\pi)\n\n与  x  轴所围图形的面积。\n该面积为：\n\nS_n = \\int_0^{n\\pi} |\\mathrm{e}^{-x} \\sin x| \\, dx\n\n将其拆分为  n  个长度为  \\pi  的区间，并利用周期性和换元法，得：\n\nS_n = \\sum_{k=0}^{n-1} \\mathrm{e}^{-k\\pi} \\cdot \\int_0^\\pi \\mathrm{e}^{-t} \\sin t \\, dt\n\n其中：\n\n\\int_0^\\pi \\mathrm{e}^{-t} \\sin t \\, dt = \\frac{1 + \\mathrm{e}^{-\\pi}}{2}\n\n因此：\n\nS_n = \\frac{1 + \\mathrm{e}^{-\\pi}}{2} \\cdot \\sum_{k=0}^{n-1} \\mathrm{e}^{-k\\pi} = \\frac{1 + \\mathrm{e}^{-\\pi}}{2} \\cdot \\frac{1 - \\mathrm{e}^{-n\\pi}}{1 - \\mathrm{e}^{-\\pi}}\n\n最终简化为：\n\nS_n = \\frac{1 - \\mathrm{e}^{-n\\pi}}{2(1 - \\mathrm{e}^{-\\pi})}\n\n极限为：\n\n\\lim_{n \\to \\infty} S_n = \\frac{1}{2(1 - \\mathrm{e}^{-\\pi})}','111111',5,2,'我的答案',1,0,0,3),(3,'jx','\\textbf{31. 当} \n当 x \\to 0 时, \\arcsin x - x 与 ax^b 是等价无穷小，则 (a,b) = (\\quad).\n','\\textbf{31. 当} \\\\\n当 x \\to 0 时, \\arcsin x - x 与 ax^b 是等价无穷小，则 (a,b) = \\left( \\frac{1}{6}, 3 \\right).\n\n',NULL,5,1,'你',2,11,0,3),(4,'jx','\\text{当 } x \\to 0 \\text{ 时，函数}\n$$\nf(x) = ax + bx^2 + \\ln(1 + x)\n$$\n\\text{与}\n$$\ng(x) = 1 - \\cos x\n$$\n\\text{是等价无穷小，则}\n$$\nab = .\n$$\n\n','\\lim_{x \\to 0} \\frac{f(x)}{g(x)} = \\lim_{x \\to 0} \\frac{ax + bx^2 + \\ln(1 + x)}{1 - \\cos x} = 1\n\n\\ln(1 + x) \\approx x - \\frac{x^2}{2}, \\quad 1 - \\cos x \\approx \\frac{x^2}{2}\n\n\\lim_{x \\to 0} \\frac{(a + 1)x + (b - \\frac{1}{2})x^2}{\\frac{x^2}{2}} = 1\n\na + 1 = 0, \\quad b - \\frac{1}{2} = 0 \\implies a = -1, \\quad b = \\frac{1}{2}\n\nab = (-1) \\cdot \\frac{1}{2} = -\\frac{1}{2}\n','111',5,1,'2',2,4,0,3),(5,'jx','\\text{已知}\n\\lim_{x \\to +\\infty} \\frac{\\int_0^x t^2 e^{x^2 - t^2} \\, dt + a e^{x^2}}{x^b} = -\\frac{1}{2}\n\\text{求 } a \\text{ 和 } b \\text{ 的值。}','\\text{解析过程：}\n\n\\int_0^x t^2 e^{x^2 - t^2} \\, dt = \\frac{1}{2} (-x^2 e^{x^2} + x^2 + e^{x^2} - 1)\n\n\\lim_{x \\to +\\infty} \\frac{\\frac{1}{2} (-x^2 e^{x^2} + x^2 + e^{x^2} - 1) + a e^{x^2}}{x^b} = -\\frac{1}{2}\n\n\\left( \\frac{1}{2} + a \\right) e^{x^2} - \\frac{1}{2} x^2 e^{x^2} \\approx -\\frac{1}{2} x^b\n\n\\frac{1}{2} + a = 0 \\implies a = -\\frac{1}{2}, \\quad b = 2\n',NULL,5,2,'你的',5,5,0,3),(6,'jx','\\lim_{n \\to \\infty} \\frac{1}{n^2} \\left( \\sin \\frac{1}{n} + 2 \\sin \\frac{2}{n} + \\cdots + n \\sin \\frac{n}{n} \\right)\n\n','S_n = \\sum_{k=1}^{n} k \\sin \\frac{k}{n} \\approx \\sum_{k=1}^{n} \\left( \\frac{k^2}{n} - \\frac{k^4}{6n^3} \\right)\n\n\\sum_{k=1}^{n} \\frac{k^2}{n} = \\frac{(n+1)(2n+1)}{6}, \\quad \\sum_{k=1}^{n} \\frac{k^4}{6n^3} = \\frac{(n+1)(2n+1)(3n^2+3n-1)}{180n^2}\nS_n \\approx \\frac{(n+1)(2n+1)}{6} - \\frac{(n+1)(2n+1)(3n^2+3n-1)}{180n^2}\n\n\\lim_{n \\to \\infty} \\frac{1}{n^2} \\left( \\frac{(n+1)(2n+1)}{6} - \\frac{(n+1)(2n+1)(3n^2+3n-1)}{180n^2} \\right) = \\frac{1}{3}\n\n',NULL,5,1,'*',0,6,0,3),(7,'jx','\\lim_{n \\to \\infty} \\sqrt[n]{1 + |x|^{3n}}\n','\\text{当 } |x| < 1 \\text{ 时，} \\lim_{n \\to \\infty} \\sqrt[n]{1 + |x|^{3n}} = 1\n\n\\text{当 } |x| = 1 \\text{ 时，} \\lim_{n \\to \\infty} \\sqrt[n]{1 + |x|^{3n}} = 1\n\n\\text{当 } |x| > 1 \\text{ 时，} \\lim_{n \\to \\infty} \\sqrt[n]{1 + |x|^{3n}} = |x|^3\n\n\\therefore \\lim_{n \\to \\infty} \\sqrt[n]{1 + |x|^{3n}} =\n\\begin{cases}\n1 & \\text{if } |x| \\leq 1 \\\\\n|x|^3 & \\text{if } |x| > 1\n\\end{cases}\n\n',NULL,5,3,'111111',2,10,0,3),(8,'jx','\\textbf{31. 当} \\\\\n当 x \\to 0 时, \\arcsin x - x 与 ax^b 是等价无穷小，则 (a,b) = \\left( \\frac{1}{6}, 3 \\right).','\\left(1 + \\frac{1}{n}\\right)^n = e \\left(1 - \\frac{1}{2n} + O\\left(\\frac{1}{n^2}\\right)\\right)\n\\left(1 + \\frac{1}{n}\\right)^n - e = -\\frac{e}{2n} + O\\left(\\frac{1}{n^2}\\right)\n\n-\\frac{e}{2n} \\sim \\frac{a}{n} \\implies a = -\\frac{e}{2}\n',NULL,5,3,'测测',0,4,0,3),(9,'jx','\\text{设存在 } 0 < \\theta < 1 \\text{，使得}\n\\arcsin x = \\frac{x}{\\sqrt{1 - (\\theta x)^2}}, \\quad -1 \\leq x \\leq 1\n\\text{，则 }\n\\lim_{x \\to 0} \\theta = .\n','\\text{设存在 } 0 < \\theta < 1 \\text{，使得}\n\n\\arcsin x = \\frac{x}{\\sqrt{1 - (\\theta x)^2}}, \\quad -1 \\leq x \\leq 1\n\n\\text{，则 }\n\n\\lim_{x \\to 0} \\theta = .\n\n',NULL,5,2,'111111',6,2,0,3),(10,'jx','\\text{设正项数列 } \\{a_n\\} \\text{ 单调减少，} \\lim_{n \\to \\infty} a_n = a \\text{，则 }\n\\lim_{n \\to \\infty} (a_1^n + a_2^n + \\cdots + a_n^n)^{\\frac{1}{n}} = .\n','\\text{解析过程：}\n\n\\text{当 } a > 0 \\text{ 时，} a_n \\leq (a_1^n + a_2^n + \\cdots + a_n^n)^{\\frac{1}{n}} \\leq n^{\\frac{1}{n}} a_n\n\n\\lim_{n \\to \\infty} n^{\\frac{1}{n}} = 1 \\text{ 和 } \\lim_{n \\to \\infty} a_n = a \\implies \\lim_{n \\to \\infty} (a_1^n + a_2^n + \\cdots + a_n^n)^{\\frac{1}{n}} = a\n\n\\text{当 } a = 0 \\text{ 时，} \\lim_{n \\to \\infty} (a_1^n + a_2^n + \\cdots + a_n^n)^{\\frac{1}{n}} = 0\n\n\\therefore \\lim_{n \\to \\infty} (a_1^n + a_2^n + \\cdots + a_n^n)^{\\frac{1}{n}} = a\n\n',NULL,5,1,'测',6,6,0,3),(11,'jx','\\text{(II) 设数列 } \\{x_n\\} \\text{ 满足}\n\\ln x_n + \\frac{1}{x_{n+1}} < 1\n\\text{，证明 } \\lim_{n \\to \\infty} x_n \\text{ 存在，并求此极限。}\n','\\ln L + \\frac{1}{L} \\leq 1\n\n\\text{定义函数 } f(x) = \\ln x + \\frac{1}{x}, \\quad f\'(x) = \\frac{1}{x} - \\frac{1}{x^2} = \\frac{x-1}{x^2}\n\n\\text{当 } x > 1 \\text{ 时，} f\'(x) > 0, \\text{ 当 } x < 1 \\text{ 时，} f\'(x) < 0, \\text{ 因此 } f(x) \\text{ 在 } x = 1 \\text{ 处取得极小值。}\n\nf(1) = \\ln 1 + \\frac{1}{1} = 1\n\n\\text{因此，当 } x = 1 \\text{ 时，} f(x) = 1 \\text{，且这是 } f(x) \\leq 1 \\text{ 的唯一解。}\n\n\\therefore \\lim_{n \\to \\infty} x_n = 1',NULL,5,2,'111111',9,0,0,3),(12,'wf','\\text{设 } f(x) = \\ln(1 - x) - \\ln(1 + x), \\quad -1 < x < 1 \\text{，则 }f\'\'(0) = .\n','\\text{解析过程：}\n\nf\'(x) = \\frac{-1}{1 - x} - \\frac{1}{1 + x}\n\nf\'\'(x) = \\frac{1}{(1 - x)^2} - \\frac{1}{(1 + x)^2}\n\nf\'\'(0) = \\frac{1}{(1 - 0)^2} - \\frac{1}{(1 + 0)^2} = 1 - 1 = 0\n\n\\therefore f\'\'(0) = 0\n\n',NULL,5,2,NULL,3,7,0,3),(13,'wf','\\text{设 } f(x) \\text{ 在点 } x = a \\text{ 处可导，且 } f(a) \\neq 0 \\text{，计算}\nI = \\lim_{x \\to \\infty} \\left[ \\frac{f\\left(a + \\frac{1}{x}\\right)}{f(a)} \\right]^x.','\\text{令 } y = \\frac{1}{x}, \\text{ 则当 } x \\to \\infty \\text{ 时，} y \\to 0.\n\nI = \\lim_{y \\to 0} \\left[ \\frac{f(a + y)}{f(a)} \\right]^{1/y}.\n\n\\ln I = \\lim_{y \\to 0} \\frac{\\ln \\left( \\frac{f(a + y)}{f(a)} \\right)}{y}.\n\n\\ln I = \\lim_{y \\to 0} \\frac{\\frac{f\'(a + y)}{f(a + y)}}{1} = \\frac{f\'(a)}{f(a)}.\n\n\\ln I = \\frac{f\'(a)}{f(a)} \\implies I = e^{\\frac{f\'(a)}{f(a)}}.\n\n\\therefore I = e^{\\frac{f\'(a)}{f(a)}}.\n\n',NULL,5,3,NULL,2,6,0,3),(14,'wf','\\text{设 } f(x) \\text{ 在 } x = 0 \\text{ 处存在二阶导数，且 } \\lim_{x \\to 0} \\frac{f(x) + x}{1 - \\cos x} = 2 \\text{，则 }\nf\'\'(0) = .\n\n','\\text{使用洛必达法则求解极限：}\n\n\\lim_{x \\to 0} \\frac{f(x) + x}{1 - \\cos x} = \\lim_{x \\to 0} \\frac{f\'(x) + 1}{\\sin x}.\n\n\\text{再次应用洛必达法则：}\n\n\\lim_{x \\to 0} \\frac{f\'(x) + 1}{\\sin x} = \\lim_{x \\to 0} \\frac{f\'\'(x)}{\\cos x}.\n\n\\text{由于 } \\cos x \\text{ 在 } x = 0 \\text{ 处等于 1，因此：}\n\n\\lim_{x \\to 0} \\frac{f\'\'(x)}{\\cos x} = f\'\'(0).\n\n\\text{根据题目条件，这个极限等于 2：}\n\nf\'\'(0) = 2.\n\n\\therefore f\'\'(0) = 2.\n\n',NULL,5,1,'测',6,6,1,3),(15,'wf','\\text{函数}\nf(x) = \\ln |(x-1)(x-2)(x-3)|\n\\text{的驻点个数为 } .','\\text{计算 } f(x) \\text{ 的一阶导数：}\n\nf\'(x) = \\frac{(x-2)(x-3) + (x-1)(x-3) + (x-1)(x-2)}{(x-1)(x-2)(x-3)} = \\frac{3x^2 - 12x + 11}{(x-1)(x-2)(x-3)}\n\\text{令 } f\'(x) = 0 \\text{ 求解驻点：}\n\n3x^2 - 12x + 11 = 0\n\nx = \\frac{12 \\pm \\sqrt{(-12)^2 - 4 \\cdot 3 \\cdot 11}}{2 \\cdot 3} = \\frac{12 \\pm \\sqrt{12}}{6} = 2 \\pm \\frac{\\sqrt{3}}{3}\n\n\\text{因此，有两个驻点 } x_1 = 2 + \\frac{\\sqrt{3}}{3} \\text{ 和 } x_2 = 2 - \\frac{\\sqrt{3}}{3}.\n\n\\therefore \\text{驻点个数为 } 2.\n',NULL,5,3,NULL,5,2,0,3),(16,'wf','\\text{若 } y = \\sin(e^{-\\sqrt{x}}) \\text{，则 }\n\\left.\\frac{dy}{dx}\\right|_{x=1} = .\n','\\text{使用链式法则求导：}\n\n\\frac{dy}{dx} = \\cos(e^{-\\sqrt{x}}) \\cdot \\frac{d}{dx}(e^{-\\sqrt{x}})\n\n\\frac{d}{dx}(e^{-\\sqrt{x}}) = e^{-\\sqrt{x}} \\cdot \\left(-\\frac{1}{2\\sqrt{x}}\\right)\n\n\\frac{dy}{dx} = \\cos(e^{-\\sqrt{x}}) \\cdot e^{-\\sqrt{x}} \\cdot \\left(-\\frac{1}{2\\sqrt{x}}\\right)\n\n\\text{将 } x = 1 \\text{ 代入上述导数表达式中：}\n\n\\left.\\frac{dy}{dx}\\right|_{x=1} = \\cos(e^{-1}) \\cdot e^{-1} \\cdot \\left(-\\frac{1}{2}\\right) = -\\frac{1}{2} \\cdot \\cos(e^{-1}) \\cdot e^{-1}\n\n\\therefore \\left.\\frac{dy}{dx}\\right|_{x=1} = -\\frac{1}{2} \\cdot \\cos(e^{-1}) \\cdot e^{-1}.',NULL,5,3,NULL,0,7,0,3),(17,'wf','\\text{设 } z = z(x, y) \\text{ 是由方程 }\ne^{2yz} + x + y^2 + z = \\frac{7}{4}\n\\text{确定的函数，则 }\n\\left.\\mathrm{d}z\\right|_{\\left(\\frac{1}{2}, \\frac{1}{2}\\right)} = .\n','\\text{对方程 } e^{2yz} + x + y^2 + z = \\frac{7}{4} \\text{ 关于 } x \\text{ 和 } y \\text{ 求偏导数：}\n\n\\frac{\\partial F}{\\partial x} = 1, \\quad \\frac{\\partial F}{\\partial y} = 2ze^{2yz} + 2y, \\quad \\frac{\\partial F}{\\partial z} = 2ye^{2yz} + 1\n\n\\frac{\\partial z}{\\partial x} = -\\frac{1}{2ye^{2yz} + 1}, \\quad \\frac{\\partial z}{\\partial y} = -\\frac{2ze^{2yz} + 2y}{2ye^{2yz} + 1}\n\n\\text{将点 } \\left(\\frac{1}{2}, \\frac{1}{2}\\right) \\text{ 代入原方程求解 } z: e^z + z = 1 \\implies z = 0\n\n\\frac{\\partial z}{\\partial x} = -\\frac{1}{2}, \\quad \\frac{\\partial z}{\\partial y} = -\\frac{1}{2}\n\n\\left.\\mathrm{d}z\\right|_{\\left(\\frac{1}{2}, \\frac{1}{2}\\right)} = -\\frac{1}{2} \\mathrm{d}x - \\frac{1}{2} \\mathrm{d}y\n\n\\therefore \\left.\\mathrm{d}z\\right|_{\\left(\\frac{1}{2}, \\frac{1}{2}\\right)} = -\\frac{1}{2} \\mathrm{d}x - \\frac{1}{2} \\mathrm{d}y.\n',NULL,5,2,NULL,9,5,0,3),(18,'wf','\\text{若函数 } z = z(x, y) \\text{ 由方程 }\ne^{x+2y+3z} + xyz = 1\n\\text{确定，则 }\n\\left.\\mathrm{d}z\\right|_{(0,0)} = .\n','\\text{对方程 } e^{x+2y+3z} + xyz = 1 \\text{ 关于 } x \\text{ 和 } y \\text{ 求偏导数：}\n\n\\frac{\\partial F}{\\partial x} = e^{x+2y+3z} + yz, \\quad \\frac{\\partial F}{\\partial y} = 2e^{x+2y+3z} + xz, \\quad \\frac{\\partial F}{\\partial z} = 3e^{x+2y+3z} + xy\n\n\\frac{\\partial z}{\\partial x} = -\\frac{e^{x+2y+3z} + yz}{3e^{x+2y+3z} + xy}, \\quad \\frac{\\partial z}{\\partial y} = -\\frac{2e^{x+2y+3z} + xz}{3e^{x+2y+3z} + xy}\n\n\\text{将点 } (0, 0) \\text{ 代入原方程求解 } z: e^{3z} = 1 \\implies z = 0\n\n\\frac{\\partial z}{\\partial x} = -\\frac{1}{3}, \\quad \\frac{\\partial z}{\\partial y} = -\\frac{2}{3}\n\n\\left.\\mathrm{d}z\\right|_{(0,0)} = -\\frac{1}{3} \\mathrm{d}x - \\frac{2}{3} \\mathrm{d}y\n\n\\therefore \\left.\\mathrm{d}z\\right|_{(0,0)} = -\\frac{1}{3} \\mathrm{d}x - \\frac{2}{3} \\mathrm{d}y.\n\n\\end{document}\n',NULL,5,3,NULL,7,8,0,3),(19,'wf','\\text{设 } y = y(x) \\text{ 由方程 }\nx^2 - xy + y^2 = 1\n\\text{确定，则 }\n\\frac{\\mathrm{d}^2y}{\\mathrm{d}x^2} = .\n','\\text{对方程 } x^2 - xy + y^2 = 1 \\text{ 关于 } x \\text{ 求导：}\n\n2x - (y + x\\frac{dy}{dx}) + 2y\\frac{dy}{dx} = 0\n\n\\frac{dy}{dx} = \\frac{y - 2x}{2y - x}\n\n\\text{对 } \\frac{dy}{dx} \\text{ 再次关于 } x \\text{ 求导：}\n\n\\frac{\\mathrm{d}^2y}{\\mathrm{d}x^2} = \\frac{(2y - x)\\left(\\frac{y - 2x}{2y - x} - 2\\right) - (y - 2x)\\left(2\\frac{y - 2x}{2y - x} - 1\\right)}{(2y - x)^2}\n\n\\frac{\\mathrm{d}^2y}{\\mathrm{d}x^2} = \\frac{3(x - y)}{(2y - x)^2}\n\n\\therefore \\frac{\\mathrm{d}^2y}{\\mathrm{d}x^2} = \\frac{3(x - y)}{(2y - x)^2}.\n',NULL,5,1,'你',6,9,1,3),(20,'wf','\\text{设 }\n\\begin{cases}\nx = \\arctan t, \\\\\ny = 3t + t^3,\n\\end{cases}\n\\text{ 则 }\n\\left.\\frac{d^2y}{dx^2}\\right|_{t=1} = .','\\text{计算 } x \\text{ 和 } y \\text{ 关于 } t \\text{ 的一阶导数：}\n\n\\frac{dx}{dt} = \\frac{1}{1+t^2}, \\quad \\frac{dy}{dt} = 3 + 3t^2\n\n\\frac{dy}{dx} = \\frac{3 + 3t^2}{\\frac{1}{1+t^2}} = 3 + 6t^2 + 3t^4\n\n\\text{计算 } y \\text{ 关于 } x \\text{ 的二阶导数：}\n\n\\frac{d}{dt}\\left(3 + 6t^2 + 3t^4\\right) = 12t + 12t^3\n\n\\frac{d^2y}{dx^2} = \\frac{12t + 12t^3}{\\frac{1}{1+t^2}} = 12t + 24t^3 + 12t^5\n\n\\left.\\frac{d^2y}{dx^2}\\right|_{t=1} = 12(1) + 24(1)^3 + 12(1)^5 = 48\n\n\\therefore \\left.\\frac{d^2y}{dx^2}\\right|_{t=1} = 48.\n',NULL,5,2,'对吗',0,0,0,3),(21,'wf','\\text{设函数 } y = y(x) \\text{ 由 }\n\\begin{cases}\nx = t^2 - 2t + 1, \\\\\ne^y \\sin t - y + 1 = 0\n\\end{cases}\n\\text{ 确定，则 }\n\\left.\\frac{\\mathrm{d}^2y}{\\mathrm{d}x^2}\\right|_{t=0} = .\n','\\text{计算 } x \\text{ 关于 } t \\text{ 的一阶导数和二阶导数：}\n\n\\frac{dx}{dt} = 2t - 2, \\quad \\frac{d^2x}{dt^2} = 2\n\n\\text{对方程 } e^y \\sin t - y + 1 = 0 \\text{ 关于 } t \\text{ 求导：}\n\n\\frac{dy}{dt} = \\frac{0}{e^y \\cos t + e^y \\sin t - 1}\n\n\\text{当 } t = 0 \\text{ 时，代入方程得到 } y = 1 \\text{，因此 } \\frac{dy}{dt} = 0\n\n\n\\frac{dy}{dx} = \\frac{\\frac{dy}{dt}}{\\frac{dx}{dt}} = 0\n\n\\frac{d^2y}{dx^2} = \\frac{\\frac{d}{dt}\\left(\\frac{dy}{dx}\\right)}{\\frac{dx}{dt}} = 0\n\n\\left.\\frac{d^2y}{dx^2}\\right|_{t=0} = 0\n\n\\therefore \\left.\\frac{d^2y}{dx^2}\\right|_{t=0} = 0.\n',NULL,5,2,NULL,2,0,0,3),(22,'jf','\\text{设 } p \\text{ 为常数，若反常积分 }\n\n\\int_{0}^{1} \\frac{\\ln x}{x^p (1-x)^{1-p}} \\, \\mathrm{d}x\n\n\\text{收敛，则 } p \\text{ 的取值范围是}.\n\n','\\text{首先分析积分在 } x = 0 \\text{ 和 } x = 1 \\text{ 处的行为。}\n\n\n\\text{当 } x \\to 0^+ \\text{ 时，} \\frac{\\ln x}{x^p} \\sim \\frac{-\\infty}{0^+} \\quad \\text{当 } p > 0. \\text{ 为了使积分在 } x = 0 \\text{ 处收敛，需要 } p < 1.\n\n\n\\text{当 } x \\to 1^- \\text{ 时，} \\frac{\\ln x}{(1-x)^{1-p}} \\sim \\frac{0}{0^-} \\quad \\text{当 } p < 1. \\text{ 为了使积分在 } x = 1 \\text{ 处收敛，需要 } p > 0.\n\\text{综合以上分析，} p \\text{ 的取值范围应满足 } 0 < p < 1.\n\n\n\\therefore p \\text{ 的取值范围是 } (0, 1).\n','(0, 1)',5,3,NULL,0,0,0,3),(23,'jf','\\text{已知 }\n\n\\int_{0}^{2} \\frac{1}{|\\ln x|^a} \\, \\mathrm{d}x\n\n\\text{收敛，} a \\text{ 为常数，则 } a \\text{ 的取值范围是}.\n','\\text{首先分析积分在 } x = 1 \\text{ 处的行为。当 } x \\text{ 接近 } 1 \\text{ 时，} \\ln x \\text{ 接近 } 0 \\text{，因此 } |\\ln x|^a \\text{ 也接近 } 0.\n\n\n\\text{当 } x \\to 1^+ \\text{ 时，} \\ln x \\sim x - 1 \\text{，因此 } |\\ln x|^a \\sim |x - 1|^a.\n\n\n\\frac{1}{|\\ln x|^a} \\sim \\frac{1}{|x - 1|^a}\n\n\\text{积分 } \\int_{0}^{2} \\frac{1}{|x - 1|^a} \\, \\mathrm{d}x \\text{ 在 } x = 1 \\text{ 处的性质与 } \\int_{0}^{1} \\frac{1}{x^a} \\, \\mathrm{d}x \\text{ 类似。}\n\n\n\\text{当 } a < 1 \\text{ 时，积分 } \\int_{0}^{1} \\frac{1}{x^a} \\, \\mathrm{d}x \\text{ 收敛；当 } a \\geq 1 \\text{ 时，积分发散。}\n\n\n\\text{因此，为了使原积分 } \\int_{0}^{2} \\frac{1}{|\\ln x|^a} \\, \\mathrm{d}x \\text{ 收敛，需要 } a < 1.\n\\therefore a \\text{ 的取值范围是 } a < 1.','a < 1',5,3,NULL,0,2,0,3),(24,'jf','\\int_{-\\infty}^{+\\infty} |x| e^{-x^2} \\, \\mathrm{d}x','\\text{由于被积函数 } |x| e^{-x^2} \\text{ 是偶函数，积分区间为 } (-\\infty, +\\infty)，\\text{ 可以利用对称性简化计算。}\n\n\n\\int_{-\\infty}^{+\\infty} |x| e^{-x^2} \\, \\mathrm{d}x = 2 \\int_{0}^{+\\infty} x e^{-x^2} \\, \\mathrm{d}x\n\n\n\\text{接下来计算 } \\int_{0}^{+\\infty} x e^{-x^2} \\, \\mathrm{d}x。 \\text{ 使用换元法，令 } u = x^2，\\text{ 则 } \\mathrm{d}u = 2x \\, \\mathrm{d}x，\\text{ 当 } x = 0 \\text{ 时 } u = 0，\\text{ 当 } x \\to +\\infty \\text{ 时 } u \\to +\\infty。\n\n\n\\int_{0}^{+\\infty} x e^{-x^2} \\, \\mathrm{d}x = \\frac{1}{2} \\int_{0}^{+\\infty} e^{-u} \\, \\mathrm{d}u\n\n\n\\text{计算 } \\int_{0}^{+\\infty} e^{-u} \\, \\mathrm{d}u，\\text{ 这是一个标准的指数函数积分，结果为 } 1。\n\n\n\\int_{0}^{+\\infty} e^{-u} \\, \\mathrm{d}u = 1\n\n\n\\int_{0}^{+\\infty} x e^{-x^2} \\, \\mathrm{d}x = \\frac{1}{2} \\cdot 1 = \\frac{1}{2}\n\n\n\\text{最终结果为：}\n\n\n\\int_{-\\infty}^{+\\infty} |x| e^{-x^2} \\, \\mathrm{d}x = 2 \\cdot \\frac{1}{2} = 1\n\n\n\\therefore \\int_{-\\infty}^{+\\infty} |x| e^{-x^2} \\, \\mathrm{d}x = 1.\n','1',5,1,NULL,1,3,0,3),(25,'jf','\\int_{0}^{1} \\ln \\frac{1}{1-x} \\, \\mathrm{d}x','\\text{首先利用对数的性质简化被积函数。}\n\n\n\\ln \\frac{1}{1-x} = -\\ln (1-x)\n\n\n\\text{因此，原积分可以写为：}\n\n\n\\int_{0}^{1} -\\ln (1-x) \\, \\mathrm{d}x = -\\int_{0}^{1} \\ln (1-x) \\, \\mathrm{d}x\n\n\n\\text{接下来计算 } \\int_{0}^{1} \\ln (1-x) \\, \\mathrm{d}x。 \\text{ 使用换元法，令 } u = 1-x，\\text{ 则 } \\mathrm{d}u = -\\mathrm{d}x，\\text{ 当 } x = 0 \\text{ 时 } u = 1，\\text{ 当 } x = 1 \\text{ 时 } u = 0。\n\n\n\\int_{0}^{1} \\ln (1-x) \\, \\mathrm{d}x = -\\int_{1}^{0} \\ln u \\, \\mathrm{d}u = \\int_{0}^{1} \\ln u \\, \\mathrm{d}u\n\n\n\\text{计算 } \\int_{0}^{1} \\ln u \\, \\mathrm{d}u。 \\text{ 使用分部积分法，令 } v = \\ln u \\text{ 和 } \\mathrm{d}w = \\mathrm{d}u，\\text{ 则 } \\mathrm{d}v = \\frac{1}{u} \\, \\mathrm{d}u \\text{ 和 } w = u。\n\n\n\\int_{0}^{1} \\ln u \\, \\mathrm{d}u = \\left[ u \\ln u \\right]_{0}^{1} - \\int_{0}^{1} u \\cdot \\frac{1}{u} \\, \\mathrm{d}u = \\left[ u \\ln u \\right]_{0}^{1} - \\int_{0}^{1} \\, \\mathrm{d}u\n\n\n\\text{计算极限值：}\n\n\n\\left[ u \\ln u \\right]_{0}^{1} = \\lim_{u \\to 1} (u \\ln u) - \\lim_{u \\to 0^+} (u \\ln u) = 0 - 0 = 0\n\n\n\\int_{0}^{1} \\, \\mathrm{d}u = 1\n\n\n\\int_{0}^{1} \\ln u \\, \\mathrm{d}u = 0 - 1 = -1\n\n\n\\text{最终结果为：}\n\n\n\\int_{0}^{1} \\ln \\frac{1}{1-x} \\, \\mathrm{d}x = -(-1) = 1\n\n\n\\therefore \\int_{0}^{1} \\ln \\frac{1}{1-x} \\, \\mathrm{d}x = 1.\n','1',5,1,NULL,0,8,0,3),(26,'jf','\\text{求 } \\int_{0}^{1} \\frac{\\mathrm{d}x}{(x+1)(x^2+1)}','\\text{首先使用部分分式分解法将被积函数 } \\frac{1}{(x+1)(x^2+1)} \\text{ 分解为更简单的形式。}\n\n\n\\frac{1}{(x+1)(x^2+1)} = \\frac{A}{x+1} + \\frac{Bx+C}{x^2+1}\n\n\n\\text{通过通分和比较系数，可以得到：}\n\n\n1 = A(x^2+1) + (Bx+C)(x+1)\n\n\n1 = Ax^2 + A + Bx^2 + Bx + Cx + C\n\n\n1 = (A+B)x^2 + (B+C)x + (A+C)\n\n\n\\text{通过比较系数，得到方程组：}\n\n\n\\begin{cases}\nA + B = 0 \\\\\nB + C = 0 \\\\\nA + C = 1\n\\end{cases}\n\n\n\\text{解这个方程组，得到 } A = \\frac{1}{2}，B = -\\frac{1}{2}，C = \\frac{1}{2}。\n\n\n\\frac{1}{(x+1)(x^2+1)} = \\frac{1/2}{x+1} + \\frac{-1/2x + 1/2}{x^2+1}\n\n\n\\text{因此，原积分可以写为：}\n\n\n\\int_{0}^{1} \\frac{\\mathrm{d}x}{(x+1)(x^2+1)} = \\int_{0}^{1} \\left( \\frac{1/2}{x+1} + \\frac{-1/2x + 1/2}{x^2+1} \\right) \\, \\mathrm{d}x\n\n\n\\text{分别计算两个积分：}\n\n\n\\int_{0}^{1} \\frac{1/2}{x+1} \\, \\mathrm{d}x = \\frac{1}{2} \\ln |x+1| \\Bigg|_{0}^{1} = \\frac{1}{2} (\\ln 2 - \\ln 1) = \\frac{1}{2} \\ln 2\n\n\n\\int_{0}^{1} \\frac{-1/2x + 1/2}{x^2+1} \\, \\mathrm{d}x = -\\frac{1}{2} \\int_{0}^{1} \\frac{x}{x^2+1} \\, \\mathrm{d}x + \\frac{1}{2} \\int_{0}^{1} \\frac{1}{x^2+1} \\, \\mathrm{d}x\n\n\n-\\frac{1}{2} \\int_{0}^{1} \\frac{x}{x^2+1} \\, \\mathrm{d}x = -\\frac{1}{4} \\ln |x^2+1| \\Bigg|_{0}^{1} = -\\frac{1}{4} (\\ln 2 - \\ln 1) = -\\frac{1}{4} \\ln 2\n\n\n\\frac{1}{2} \\int_{0}^{1} \\frac{1}{x^2+1} \\, \\mathrm{d}x = \\frac{1}{2} \\arctan x \\Bigg|_{0}^{1} = \\frac{1}{2} \\left( \\frac{\\pi}{4} - 0 \\right) = \\frac{\\pi}{8}\n\n\n\\text{最终结果为：}\n\n\n\\int_{0}^{1} \\frac{\\mathrm{d}x}{(x+1)(x^2+1)} = \\frac{1}{2} \\ln 2 - \\frac{1}{4} \\ln 2 + \\frac{\\pi}{8} = \\frac{1}{4} \\ln 2 + \\frac{\\pi}{8}\n\n\n\\therefore \\int_{0}^{1} \\frac{\\mathrm{d}x}{(x+1)(x^2+1)} = \\frac{1}{4} \\ln 2 + \\frac{\\pi}{8}.\n','\\frac{1}{4} \\ln 2 + \\frac{\\pi}{8}',5,1,NULL,7,0,0,3),(27,'jf','\\text{计算不定积分 } \\int e^{2x} \\arctan \\sqrt{e^x - 1} \\, \\mathrm{d}x','\\text{使用换元法和分部积分法来求解这个不定积分。}\n\n\n\\text{令 } u = \\sqrt{e^x - 1}，\\text{ 则 } u^2 = e^x - 1 \\Rightarrow e^x = u^2 + 1 \\Rightarrow x = \\ln(u^2 + 1)\n\n\n\\mathrm{d}x = \\frac{2u}{u^2 + 1} \\, \\mathrm{d}u\n\n\n\\text{将 } e^{2x} \\text{ 和 } \\mathrm{d}x \\text{ 替换为 } u \\text{ 的表达式：}\n\n\ne^{2x} = (e^x)^2 = (u^2 + 1)^2\n\n\n\\int e^{2x} \\arctan \\sqrt{e^x - 1} \\, \\mathrm{d}x = \\int (u^2 + 1)^2 \\arctan u \\cdot \\frac{2u}{u^2 + 1} \\, \\mathrm{d}u\n\n\n= 2 \\int u(u^2 + 1) \\arctan u \\, \\mathrm{d}u\n\n\n\\text{接下来使用分部积分法，令 } v = \\arctan u \\text{ 和 } \\mathrm{d}w = u(u^2 + 1) \\, \\mathrm{d}u，\\text{ 则 } \\mathrm{d}v = \\frac{1}{u^2 + 1} \\, \\mathrm{d}u \\text{ 和 } w = \\frac{1}{4}(u^2 + 1)^2。\n\n\n\\int u(u^2 + 1) \\arctan u \\, \\mathrm{d}u = \\left[ \\frac{1}{4}(u^2 + 1)^2 \\arctan u \\right] - \\int \\frac{1}{4}(u^2 + 1)^2 \\cdot \\frac{1}{u^2 + 1} \\, \\mathrm{d}u\n\n\n= \\frac{1}{4}(u^2 + 1)^2 \\arctan u - \\frac{1}{4} \\int (u^2 + 1) \\, \\mathrm{d}u\n\n\n= \\frac{1}{4}(u^2 + 1)^2 \\arctan u - \\frac{1}{4} \\left( \\frac{u^3}{3} + u \\right) + C\n\n\n\\text{最终结果为：}\n\n\n\\int e^{2x} \\arctan \\sqrt{e^x - 1} \\, \\mathrm{d}x = 2 \\left( \\frac{1}{4}(u^2 + 1)^2 \\arctan u - \\frac{1}{4} \\left( \\frac{u^3}{3} + u \\right) \\right) + C\n\n\n= \\frac{1}{2}(u^2 + 1)^2 \\arctan u - \\frac{1}{6}u^3 - \\frac{1}{2}u + C\n\n\n= \\frac{1}{2}(e^x - 1 + 1)^2 \\arctan \\sqrt{e^x - 1} - \\frac{1}{6}(\\sqrt{e^x - 1})^3 - \\frac{1}{2}\\sqrt{e^x - 1} + C\n\n\n= \\frac{1}{2}e^{2x} \\arctan \\sqrt{e^x - 1} - \\frac{1}{6}(e^x - 1)^{3/2} - \\frac{1}{2}\\sqrt{e^x - 1} + C\n\n\n\\therefore \\int e^{2x} \\arctan \\sqrt{e^x - 1} \\, \\mathrm{d}x = \\frac{1}{2}e^{2x} \\arctan \\sqrt{e^x - 1} - \\frac{1}{6}(e^x - 1)^{3/2} - \\frac{1}{2}\\sqrt{e^x - 1} + C.\n','\\frac{1}{2}e^{2x} \\arctan \\sqrt{e^x - 1} - \\frac{1}{6}(e^x - 1)^{3/2} - \\frac{1}{2}\\sqrt{e^x - 1} + C',5,2,NULL,3,6,0,3),(28,'jf','\\text{求 } \\int \\frac{x + 2}{(2x + 1)(x^2 + x + 1)} \\, \\mathrm{d}x','\\text{首先使用部分分式分解法将被积函数 } \\frac{x + 2}{(2x + 1)(x^2 + x + 1)} \\text{ 分解为更简单的形式。}\n\n\n\\frac{x + 2}{(2x + 1)(x^2 + x + 1)} = \\frac{A}{2x + 1} + \\frac{Bx + C}{x^2 + x + 1}\n\n\n\\text{通过通分和比较系数，可以得到：}\n\n\nx + 2 = A(x^2 + x + 1) + (Bx + C)(2x + 1)\n\n\nx + 2 = Ax^2 + Ax + A + 2Bx^2 + Bx + 2Cx + C\n\n\nx + 2 = (A + 2B)x^2 + (A + B + 2C)x + (A + C)\n\n\n\\text{通过比较系数，得到方程组：}\n\n\n\\begin{cases}\nA + 2B = 0 \\\\\nA + B + 2C = 1 \\\\\nA + C = 2\n\\end{cases}\n\n\n\\text{解这个方程组，得到 } A = 1，B = -\\frac{1}{2}，C = 1。\n\n\n\\frac{x + 2}{(2x + 1)(x^2 + x + 1)} = \\frac{1}{2x + 1} + \\frac{-\\frac{1}{2}x + 1}{x^2 + x + 1}\n\n\n\\text{因此，原积分可以写为：}\n\n\n\\int \\frac{x + 2}{(2x + 1)(x^2 + x + 1)} \\, \\mathrm{d}x = \\int \\left( \\frac{1}{2x + 1} + \\frac{-\\frac{1}{2}x + 1}{x^2 + x + 1} \\right) \\, \\mathrm{d}x\n\n\n\\text{分别计算两个积分：}\n\n\n\\int \\frac{1}{2x + 1} \\, \\mathrm{d}x = \\frac{1}{2} \\ln |2x + 1|\n\n\n\\int \\frac{-\\frac{1}{2}x + 1}{x^2 + x + 1} \\, \\mathrm{d}x = -\\frac{1}{2} \\int \\frac{x}{x^2 + x + 1} \\, \\mathrm{d}x + \\int \\frac{1}{x^2 + x + 1} \\, \\mathrm{d}x\n\n\n-\\frac{1}{2} \\int \\frac{x}{x^2 + x + 1} \\, \\mathrm{d}x = -\\frac{1}{4} \\ln |x^2 + x + 1|\n\n\n\\int \\frac{1}{x^2 + x + 1} \\, \\mathrm{d}x = \\int \\frac{1}{\\left(x + \\frac{1}{2}\\right)^2 + \\frac{3}{4}} \\, \\mathrm{d}x = \\frac{2}{\\sqrt{3}} \\arctan \\left( \\frac{2x + 1}{\\sqrt{3}} \\right)\n\n\n\\text{最终结果为：}\n\n\n\\int \\frac{x + 2}{(2x + 1)(x^2 + x + 1)} \\, \\mathrm{d}x = \\frac{1}{2} \\ln |2x + 1| - \\frac{1}{4} \\ln |x^2 + x + 1| + \\frac{2}{\\sqrt{3}} \\arctan \\left( \\frac{2x + 1}{\\sqrt{3}} \\right) + C\n\n\n\\therefore \\int \\frac{x + 2}{(2x + 1)(x^2 + x + 1)} \\, \\mathrm{d}x = \\frac{1}{2} \\ln |2x + 1| - \\frac{1}{4} \\ln |x^2 + x + 1| + \\frac{2}{\\sqrt{3}} \\arctan \\left( \\frac{2x + 1}{\\sqrt{3}} \\right) + C.\n','\\frac{1}{2} \\ln |2x + 1| - \\frac{1}{4} \\ln |x^2 + x + 1| + \\frac{2}{\\sqrt{3}} \\arctan \\left( \\frac{2x + 1}{\\sqrt{3}} \\right) + C',5,2,NULL,8,0,0,3),(29,'jf','\\frac{1}{2} \\ln |2x + 1| - \\frac{1}{4} \\ln |x^2 + x + 1| + \\frac{2}{\\sqrt{3}} \\arctan \\left( \\frac{2x + 1}{\\sqrt{3}} \\right) + C','\\text{首先找到两条曲线 } y = \\sqrt{x} \\text{ 和 } y = x^2 \\text{ 的交点。}\n\n\n\\sqrt{x} = x^2 \\Rightarrow x = 0 \\text{ 或 } x = 1\n\n\n\\text{因此，所围区域在 } x \\in [0, 1] \\text{ 范围内。}\n\n\n\\text{接下来，使用极坐标变换和旋转体体积公式来计算体积。由于是绕 } y = x \\text{ 旋转，可以使用二重积分的方法。}\n\n\nV = \\int_{0}^{1} \\int_{x^2}^{\\sqrt{x}} 2\\pi \\cdot \\frac{|x - y|}{\\sqrt{2}} \\, \\mathrm{d}y \\, \\mathrm{d}x\n\n\n\\text{其中，} \\frac{|x - y|}{\\sqrt{2}} \\text{ 是从点 } (x, y) \\text{ 到直线 } y = x \\text{ 的距离。}\n\n\n\\text{计算内层积分：}\n\n\n\\int_{x^2}^{\\sqrt{x}} \\frac{|x - y|}{\\sqrt{2}} \\, \\mathrm{d}y = \\frac{1}{\\sqrt{2}} \\int_{x^2}^{\\sqrt{x}} (y - x) \\, \\mathrm{d}y\n\n\n= \\frac{1}{\\sqrt{2}} \\left[ \\frac{(y - x)^2}{2} \\right]_{x^2}^{\\sqrt{x}}\n\n\n= \\frac{1}{2\\sqrt{2}} \\left[ (\\sqrt{x} - x)^2 - (x^2 - x)^2 \\right]\n\n\n\\text{然后计算外层积分：}\n\n\nV = 2\\pi \\int_{0}^{1} \\frac{1}{2\\sqrt{2}} \\left[ (\\sqrt{x} - x)^2 - (x^2 - x)^2 \\right] \\, \\mathrm{d}x\n\n\n= \\frac{\\pi}{\\sqrt{2}} \\int_{0}^{1} \\left[ (\\sqrt{x} - x)^2 - (x^2 - x)^2 \\right] \\, \\mathrm{d}x\n\n\n\\text{展开并计算积分：}\n\n\n(\\sqrt{x} - x)^2 = x - 2x\\sqrt{x} + x^2\n\n\n(x^2 - x)^2 = x^4 - 2x^3 + x^2\n\n\nV = \\frac{\\pi}{\\sqrt{2}} \\int_{0}^{1} \\left[ x - 2x\\sqrt{x} + x^2 - x^4 + 2x^3 - x^2 \\right] \\, \\mathrm{d}x\n\n\n= \\frac{\\pi}{\\sqrt{2}} \\int_{0}^{1} \\left[ x - 2x\\sqrt{x} - x^4 + 2x^3 \\right] \\, \\mathrm{d}x\n\n\n= \\frac{\\pi}{\\sqrt{2}} \\left[ \\frac{x^2}{2} - \\frac{4x^{5/2}}{5} - \\frac{x^5}{5} + \\frac{x^4}{2} \\right]_{0}^{1}\n\n\n= \\frac{\\pi}{\\sqrt{2}} \\left( \\frac{1}{2} - \\frac{4}{5} - \\frac{1}{5} + \\frac{1}{2} \\right)\n\n\n= \\frac{\\pi}{\\sqrt{2}} \\left( 1 - \\frac{4}{5} - \\frac{1}{5} \\right)\n\n\n= \\frac{\\pi}{\\sqrt{2}} \\cdot \\frac{1}{5}\n\n\n= \\frac{\\pi}{5\\sqrt{2}}\n\n\n\\therefore \\text{旋转体的体积为 } \\frac{\\pi}{5\\sqrt{2}}.\n','\\frac{\\pi}{5\\sqrt{2}}',5,3,NULL,0,2,0,3),(30,'jf','\\text{若函数 } f(x) \\text{ 的二阶导数连续，且满足 } f\'\'(x) - f(x) = x \\text{，则 }\n\n\n\\int_{-\\pi}^{\\pi} f(x) \\cos x \\, \\mathrm{d}x = ?\n','\\text{首先解微分方程 } f\'\'(x) - f(x) = x。\n\n\n\\text{这是一个非齐次线性微分方程。首先求解对应的齐次方程 } f\'\'(x) - f(x) = 0。\n\n\nr^2 - 1 = 0 \\Rightarrow r = \\pm 1\n\n\n\\text{因此，齐次方程的通解为：}\n\n\nf_h(x) = C_1 e^x + C_2 e^{-x}\n\n\n\\text{接下来求特解。假设特解形式为 } f_p(x) = Ax + B。\n\n\nf_p\'(x) = A, \\quad f_p\'\'(x) = 0\n\n\n\\text{代入原方程：}\n\n\n0 - (Ax + B) = x \\Rightarrow -Ax - B = x\n\n\n\\text{比较系数得：}\n\n\n-A = 1 \\Rightarrow A = -1\n\n\n-B = 0 \\Rightarrow B = 0\n\n\n\\text{因此，特解为：}\n\n\nf_p(x) = -x\n\n\n\\text{所以，原方程的通解为：}\n\n\nf(x) = C_1 e^x + C_2 e^{-x} - x\n\n\n\\text{接下来计算积分 } \\int_{-\\pi}^{\\pi} f(x) \\cos x \\, \\mathrm{d}x。\n\n\n\\int_{-\\pi}^{\\pi} f(x) \\cos x \\, \\mathrm{d}x = \\int_{-\\pi}^{\\pi} (C_1 e^x + C_2 e^{-x} - x) \\cos x \\, \\mathrm{d}x\n\n\n\\text{分别计算三个部分的积分：}\n\n\n\\int_{-\\pi}^{\\pi} C_1 e^x \\cos x \\, \\mathrm{d}x, \\quad \\int_{-\\pi}^{\\pi} C_2 e^{-x} \\cos x \\, \\mathrm{d}x, \\quad \\int_{-\\pi}^{\\pi} (-x) \\cos x \\, \\mathrm{d}x\n\n\n\\text{对于 } \\int_{-\\pi}^{\\pi} C_1 e^x \\cos x \\, \\mathrm{d}x \\text{ 和 } \\int_{-\\pi}^{\\pi} C_2 e^{-x} \\cos x \\, \\mathrm{d}x \\text{，由于被积函数是奇函数乘以偶函数，结果为0。}\n\n\n\\int_{-\\pi}^{\\pi} C_1 e^x \\cos x \\, \\mathrm{d}x = 0, \\quad \\int_{-\\pi}^{\\pi} C_2 e^{-x} \\cos x \\, \\mathrm{d}x = 0\n\n\n\\text{对于 } \\int_{-\\pi}^{\\pi} (-x) \\cos x \\, \\mathrm{d}x \\text{，使用分部积分：}\n\n\n\\int_{-\\pi}^{\\pi} (-x) \\cos x \\, \\mathrm{d}x = -\\left[ x \\sin x \\right]_{-\\pi}^{\\pi} + \\int_{-\\pi}^{\\pi} \\sin x \\, \\mathrm{d}x\n\n\n= -\\left[ x \\sin x \\right]_{-\\pi}^{\\pi} + \\left[ -\\cos x \\right]_{-\\pi}^{\\pi}\n\n\n= -\\left[ \\pi \\sin \\pi - (-\\pi \\sin (-\\pi)) \\right] + \\left[ -\\cos \\pi - (-\\cos (-\\pi)) \\right]\n\n\n= -\\left[ 0 - 0 \\right] + \\left[ -(-1) - (-(-1)) \\right]\n\n\n= 0 + 0 = 0\n\n\n\\text{因此，最终结果为：}\n\n\n\\int_{-\\pi}^{\\pi} f(x) \\cos x \\, \\mathrm{d}x = 0\n\n\n\\therefore \\int_{-\\pi}^{\\pi} f(x) \\cos x \\, \\mathrm{d}x = 0.\n','0',5,2,NULL,7,8,0,3),(31,'jf','\\int_{0}^{t} \\mathrm{d}y \\int_{\\sqrt{y}}^{\\sqrt{t}} \\sqrt{1 + x^3} \\, \\mathrm{d}x = ?','\\text{首先观察积分区域。外层积分变量为 } y \\text{，从 } 0 \\text{ 到 } t \\text{；内层积分变量为 } x \\text{，从 } \\sqrt{y} \\text{ 到 } \\sqrt{t}。\n\n\n\\text{为了简化计算，可以考虑交换积分顺序。原积分区域可以用不等式表示为：}\n\n\n0 \\leq y \\leq t, \\quad \\sqrt{y} \\leq x \\leq \\sqrt{t}\n\n\n\\text{重新描述积分区域：对于固定的 } x \\text{，} y \\text{ 的范围是从 } 0 \\text{ 到 } x^2 \\text{（因为 } y \\leq x^2 \\text{），而 } x \\text{ 的范围是从 } 0 \\text{ 到 } \\sqrt{t}。\n\n\n\\text{因此，交换积分顺序后，积分变为：}\n\n\n\\int_{0}^{\\sqrt{t}} \\int_{0}^{x^2} \\sqrt{1 + x^3} \\, \\mathrm{d}y \\, \\mathrm{d}x\n\n\n\\text{计算内层积分：}\n\n\n\\int_{0}^{x^2} \\sqrt{1 + x^3} \\, \\mathrm{d}y = \\sqrt{1 + x^3} \\cdot (x^2 - 0) = x^2 \\sqrt{1 + x^3}\n\n\n\\text{然后计算外层积分：}\n\n\n\\int_{0}^{\\sqrt{t}} x^2 \\sqrt{1 + x^3} \\, \\mathrm{d}x\n\n\n\\text{使用换元法，令 } u = 1 + x^3 \\text{，则 } \\mathrm{d}u = 3x^2 \\, \\mathrm{d}x \\text{，即 } x^2 \\, \\mathrm{d}x = \\frac{1}{3} \\, \\mathrm{d}u。\n\n\n\\text{当 } x = 0 \\text{ 时，} u = 1 \\text{；当 } x = \\sqrt{t} \\text{ 时，} u = 1 + (\\sqrt{t})^3 = 1 + t^{3/2}。\n\n\n\\text{因此，积分变为：}\n\n\n\\int_{1}^{1 + t^{3/2}} \\frac{1}{3} \\sqrt{u} \\, \\mathrm{d}u = \\frac{1}{3} \\int_{1}^{1 + t^{3/2}} u^{1/2} \\, \\mathrm{d}u\n\n\n\\text{计算积分：}\n\n\n\\frac{1}{3} \\left[ \\frac{2}{3} u^{3/2} \\right]_{1}^{1 + t^{3/2}} = \\frac{2}{9} \\left[ (1 + t^{3/2})^{3/2} - 1 \\right]\n\n\n\\therefore \\int_{0}^{t} \\mathrm{d}y \\int_{\\sqrt{y}}^{\\sqrt{t}} \\sqrt{1 + x^3} \\, \\mathrm{d}x = \\frac{2}{9} \\left[ (1 + t^{3/2})^{3/2} - 1 \\right].\n','\\frac{2}{9} \\left[ (1 + t^{3/2})^{3/2} - 1 \\right]',5,1,NULL,3,0,0,3);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) NOT NULL,
  `admin_permission` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'zhangsan','1234567890',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-06 21:12:03
