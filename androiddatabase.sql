-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 26, 2018 at 08:27 AM
-- Server version: 5.7.22-0ubuntu0.16.04.1
-- PHP Version: 7.0.28-0ubuntu0.16.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `androiddatabase`
--
CREATE DATABASE IF NOT EXISTS `androiddatabase` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `androiddatabase`;

-- --------------------------------------------------------

--
-- Table structure for table `balancestudents`
--
-- Creation: Apr 24, 2018 at 01:26 AM
-- Last update: Apr 25, 2018 at 11:08 AM
--

CREATE TABLE IF NOT EXISTS `balancestudents` (
  `Students_idStudent` int(11) NOT NULL,
  `Balance` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`Students_idStudent`),
  UNIQUE KEY `Students_idStudents_UNIQUE` (`Students_idStudent`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- RELATIONS FOR TABLE `balancestudents`:
--   `Students_idStudent`
--       `students` -> `idStudent`
--

--
-- Dumping data for table `balancestudents`
--

INSERT INTO `balancestudents` (`Students_idStudent`, `Balance`) VALUES
(1, 13),
(3, 0),
(5, 0),
(6, 0);

-- --------------------------------------------------------

--
-- Table structure for table `balanceteachers`
--
-- Creation: Apr 24, 2018 at 01:26 AM
--

CREATE TABLE IF NOT EXISTS `balanceteachers` (
  `Teachers_idTeacher` int(11) NOT NULL,
  `Balance` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`Teachers_idTeacher`),
  UNIQUE KEY `Teachers_idTeacher_UNIQUE` (`Teachers_idTeacher`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- RELATIONS FOR TABLE `balanceteachers`:
--   `Teachers_idTeacher`
--       `teachers` -> `idTeacher`
--

--
-- Dumping data for table `balanceteachers`
--

INSERT INTO `balanceteachers` (`Teachers_idTeacher`, `Balance`) VALUES
(1, 0),
(2, 0);

-- --------------------------------------------------------

--
-- Table structure for table `bookid`
--
-- Creation: Apr 24, 2018 at 01:26 AM
-- Last update: Apr 26, 2018 at 02:12 AM
--

CREATE TABLE IF NOT EXISTS `bookid` (
  `BookNumberID` int(11) NOT NULL AUTO_INCREMENT,
  `BookID` int(11) NOT NULL,
  `Borrowed` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`BookNumberID`,`BookID`),
  UNIQUE KEY `BookNumberID_UNIQUE` (`BookNumberID`),
  KEY `fk_BookID_BookInfo_idx` (`BookID`)
) ENGINE=InnoDB AUTO_INCREMENT=908 DEFAULT CHARSET=latin1;

--
-- RELATIONS FOR TABLE `bookid`:
--   `BookID`
--       `bookinfo` -> `idBook`
--

--
-- Dumping data for table `bookid`
--

INSERT INTO `bookid` (`BookNumberID`, `BookID`, `Borrowed`) VALUES
(6, 7, 1),
(7, 7, 0),
(8, 7, 0),
(9, 7, 0),
(10, 7, 1),
(11, 7, 0),
(12, 7, 0),
(13, 7, 0),
(14, 7, 0),
(15, 7, 0),
(16, 7, 0),
(17, 7, 0),
(18, 7, 0),
(19, 7, 0),
(20, 7, 1),
(21, 7, 0),
(22, 7, 0),
(23, 7, 0),
(24, 7, 0),
(25, 7, 1),
(26, 8, 1),
(27, 8, 0),
(29, 8, 0),
(30, 8, 1),
(31, 8, 0),
(32, 8, 0),
(33, 8, 0),
(34, 15, 1),
(35, 15, 0),
(36, 15, 0),
(37, 20, 0),
(38, 20, 0),
(39, 20, 0),
(40, 21, 0),
(41, 21, 0),
(42, 21, 0),
(43, 21, 0),
(44, 21, 0),
(45, 21, 0),
(46, 21, 0),
(47, 21, 0),
(48, 21, 0),
(49, 21, 0),
(50, 21, 0),
(51, 21, 0),
(52, 22, 0),
(53, 22, 0),
(54, 22, 0),
(55, 22, 0),
(56, 22, 0),
(57, 22, 0),
(58, 22, 0),
(59, 22, 0),
(60, 22, 0),
(61, 22, 0),
(62, 22, 0),
(63, 22, 0),
(64, 22, 0),
(65, 22, 0),
(66, 22, 0),
(67, 22, 0),
(68, 22, 0),
(69, 22, 0),
(70, 22, 0),
(71, 22, 0),
(72, 22, 0),
(73, 22, 0),
(74, 22, 0),
(75, 22, 0),
(76, 22, 0),
(77, 22, 0),
(78, 22, 0),
(79, 22, 0),
(80, 22, 0),
(81, 22, 0),
(82, 22, 0),
(83, 22, 0),
(84, 22, 0),
(85, 22, 0),
(86, 22, 0),
(87, 22, 0),
(88, 22, 0),
(89, 22, 0),
(90, 22, 0),
(91, 22, 0),
(92, 22, 0),
(93, 22, 0),
(94, 22, 0),
(95, 22, 0),
(96, 22, 0),
(97, 22, 0),
(98, 22, 0),
(99, 22, 0),
(100, 22, 0),
(101, 22, 0),
(102, 22, 0),
(103, 22, 0),
(104, 22, 0),
(105, 22, 0),
(106, 22, 0),
(107, 22, 0),
(108, 22, 0),
(109, 22, 0),
(110, 22, 0),
(111, 22, 0),
(112, 22, 0),
(113, 22, 0),
(114, 22, 0),
(115, 22, 0),
(116, 22, 0),
(117, 22, 0),
(118, 22, 0),
(119, 22, 0),
(120, 22, 0),
(121, 22, 0),
(122, 22, 0),
(123, 22, 0),
(124, 22, 0),
(125, 22, 0),
(126, 22, 0),
(127, 22, 0),
(128, 22, 0),
(129, 22, 0),
(130, 22, 0),
(131, 22, 0),
(132, 22, 0),
(133, 22, 0),
(134, 22, 0),
(135, 22, 0),
(136, 22, 0),
(137, 23, 0),
(138, 23, 0),
(139, 23, 0),
(140, 23, 0),
(141, 23, 0),
(142, 23, 0),
(143, 23, 0),
(144, 23, 0),
(145, 23, 0),
(146, 23, 0),
(147, 23, 0),
(148, 23, 0),
(149, 23, 0),
(150, 23, 0),
(151, 23, 0),
(152, 23, 0),
(153, 23, 0),
(154, 23, 0),
(155, 23, 0),
(156, 23, 0),
(157, 23, 0),
(158, 23, 0),
(159, 23, 0),
(160, 23, 0),
(161, 23, 0),
(162, 23, 0),
(163, 23, 0),
(164, 23, 0),
(165, 23, 0),
(166, 23, 0),
(167, 23, 0),
(168, 23, 0),
(169, 23, 0),
(170, 23, 0),
(171, 23, 0),
(172, 23, 0),
(173, 23, 0),
(174, 23, 0),
(175, 23, 0),
(176, 23, 0),
(177, 23, 0),
(178, 23, 0),
(179, 23, 0),
(180, 23, 0),
(181, 23, 0),
(182, 23, 0),
(183, 23, 0),
(184, 23, 0),
(185, 23, 0),
(186, 23, 0),
(187, 23, 0),
(188, 23, 0),
(189, 23, 0),
(190, 23, 0),
(191, 23, 0),
(192, 23, 0),
(193, 23, 0),
(194, 23, 0),
(195, 23, 0),
(196, 23, 0),
(197, 23, 0),
(198, 23, 0),
(199, 23, 0),
(200, 23, 0),
(201, 23, 0),
(202, 23, 0),
(203, 23, 0),
(204, 23, 0),
(205, 23, 0),
(206, 23, 0),
(207, 23, 0),
(208, 23, 0),
(209, 23, 0),
(210, 23, 0),
(211, 23, 0),
(212, 23, 0),
(213, 23, 0),
(214, 23, 0),
(215, 23, 0),
(216, 23, 0),
(217, 23, 0),
(218, 23, 0),
(219, 23, 0),
(220, 23, 0),
(221, 23, 0),
(222, 23, 0),
(223, 23, 0),
(224, 23, 0),
(225, 23, 0),
(226, 23, 0),
(227, 23, 0),
(228, 23, 0),
(229, 23, 0),
(230, 23, 0),
(231, 23, 0),
(232, 23, 0),
(233, 23, 0),
(234, 23, 0),
(235, 23, 0),
(236, 23, 0),
(237, 23, 0),
(238, 23, 0),
(239, 23, 0),
(240, 23, 0),
(241, 23, 0),
(242, 23, 0),
(243, 23, 0),
(244, 23, 0),
(245, 23, 0),
(246, 23, 0),
(247, 23, 0),
(248, 23, 0),
(249, 23, 0),
(250, 23, 0),
(251, 23, 0),
(252, 23, 0),
(253, 23, 0),
(254, 23, 0),
(255, 23, 0),
(256, 23, 0),
(257, 23, 0),
(258, 23, 0),
(259, 23, 0),
(260, 23, 0),
(261, 23, 0),
(262, 23, 0),
(263, 23, 0),
(264, 23, 0),
(265, 23, 0),
(266, 23, 0),
(267, 23, 0),
(268, 23, 0),
(269, 23, 0),
(270, 23, 0),
(271, 23, 0),
(272, 23, 0),
(273, 23, 0),
(274, 23, 0),
(275, 23, 0),
(276, 23, 0),
(277, 23, 0),
(278, 23, 0),
(279, 23, 0),
(280, 23, 0),
(281, 23, 0),
(282, 23, 0),
(283, 23, 0),
(284, 23, 0),
(285, 23, 0),
(286, 23, 0),
(287, 23, 0),
(288, 23, 0),
(289, 23, 0),
(290, 23, 0),
(291, 23, 0),
(292, 23, 0),
(293, 23, 0),
(294, 23, 0),
(295, 23, 0),
(296, 23, 0),
(297, 23, 0),
(298, 23, 0),
(299, 23, 0),
(300, 23, 1),
(301, 23, 0),
(302, 23, 0),
(303, 23, 0),
(304, 23, 0),
(305, 23, 0),
(306, 23, 0),
(307, 23, 0),
(308, 23, 0),
(309, 23, 0),
(310, 23, 0),
(311, 23, 0),
(312, 23, 0),
(313, 23, 0),
(314, 23, 0),
(315, 23, 0),
(316, 23, 0),
(317, 23, 0),
(318, 23, 0),
(319, 23, 0),
(320, 23, 0),
(321, 23, 0),
(322, 23, 0),
(323, 23, 0),
(324, 23, 0),
(325, 23, 0),
(326, 23, 0),
(327, 23, 0),
(328, 23, 0),
(329, 23, 0),
(330, 23, 0),
(331, 23, 0),
(332, 23, 0),
(333, 23, 0),
(334, 23, 0),
(335, 23, 0),
(336, 23, 0),
(337, 23, 0),
(338, 23, 0),
(339, 23, 0),
(340, 23, 0),
(341, 23, 0),
(342, 23, 0),
(343, 23, 0),
(344, 23, 0),
(345, 23, 0),
(346, 23, 0),
(347, 23, 0),
(348, 23, 0),
(349, 23, 0),
(350, 23, 0),
(351, 23, 0),
(352, 23, 0),
(353, 23, 0),
(354, 23, 0),
(355, 23, 0),
(356, 23, 0),
(357, 23, 0),
(358, 23, 0),
(359, 23, 0),
(360, 23, 0),
(361, 23, 0),
(362, 23, 0),
(363, 23, 0),
(364, 23, 0),
(365, 23, 0),
(366, 23, 0),
(367, 23, 0),
(368, 23, 0),
(369, 23, 0),
(370, 23, 0),
(371, 23, 0),
(372, 23, 0),
(373, 23, 0),
(374, 23, 0),
(375, 23, 0),
(376, 23, 0),
(377, 23, 0),
(378, 23, 0),
(379, 23, 0),
(380, 23, 0),
(381, 23, 0),
(382, 23, 0),
(383, 23, 0),
(384, 23, 0),
(385, 23, 0),
(386, 23, 0),
(387, 23, 0),
(388, 23, 0),
(389, 23, 0),
(390, 23, 0),
(391, 23, 0),
(392, 23, 0),
(393, 23, 0),
(394, 23, 0),
(395, 23, 0),
(396, 23, 0),
(397, 23, 0),
(398, 23, 0),
(399, 23, 0),
(400, 23, 0),
(401, 23, 0),
(402, 23, 0),
(403, 23, 0),
(404, 23, 0),
(405, 23, 0),
(406, 23, 0),
(407, 23, 0),
(408, 23, 0),
(409, 23, 0),
(410, 23, 0),
(411, 23, 0),
(412, 23, 0),
(413, 23, 0),
(414, 23, 0),
(415, 23, 0),
(416, 23, 0),
(417, 23, 0),
(418, 23, 0),
(419, 23, 0),
(420, 23, 0),
(421, 23, 0),
(422, 23, 0),
(423, 23, 0),
(424, 23, 0),
(425, 23, 0),
(426, 23, 0),
(427, 23, 0),
(428, 23, 0),
(429, 23, 0),
(430, 23, 0),
(431, 23, 0),
(432, 23, 0),
(433, 23, 0),
(434, 23, 0),
(435, 23, 0),
(436, 23, 0),
(437, 23, 0),
(438, 23, 0),
(439, 23, 0),
(440, 23, 0),
(441, 23, 0),
(442, 23, 0),
(443, 23, 0),
(444, 23, 0),
(445, 23, 0),
(446, 23, 0),
(447, 23, 0),
(448, 23, 0),
(449, 23, 0),
(450, 23, 0),
(451, 23, 0),
(452, 23, 0),
(453, 23, 0),
(454, 23, 0),
(455, 23, 0),
(456, 23, 0),
(457, 23, 0),
(458, 23, 0),
(459, 23, 0),
(460, 23, 0),
(461, 23, 0),
(462, 23, 0),
(463, 23, 0),
(464, 23, 0),
(465, 23, 0),
(466, 23, 0),
(467, 23, 0),
(468, 23, 0),
(469, 23, 0),
(470, 23, 0),
(471, 23, 0),
(472, 23, 0),
(473, 23, 0),
(474, 23, 0),
(475, 23, 1),
(476, 23, 0),
(477, 23, 0),
(478, 23, 0),
(479, 23, 0),
(480, 23, 0),
(481, 23, 0),
(482, 23, 0),
(483, 23, 0),
(484, 23, 0),
(485, 23, 0),
(486, 23, 0),
(487, 23, 0),
(488, 23, 0),
(489, 23, 0),
(490, 23, 0),
(491, 23, 0),
(492, 23, 0),
(493, 23, 0),
(494, 23, 0),
(495, 23, 0),
(496, 23, 0),
(497, 23, 0),
(498, 23, 0),
(499, 23, 0),
(500, 23, 0),
(501, 23, 0),
(502, 23, 0),
(503, 23, 0),
(504, 23, 0),
(505, 23, 0),
(506, 23, 0),
(507, 23, 0),
(508, 23, 0),
(509, 23, 0),
(510, 23, 0),
(511, 23, 0),
(512, 23, 0),
(513, 23, 0),
(514, 23, 0),
(515, 23, 0),
(516, 23, 0),
(517, 23, 0),
(518, 23, 0),
(519, 23, 0),
(520, 23, 0),
(521, 23, 0),
(522, 23, 0),
(523, 23, 0),
(524, 23, 0),
(525, 23, 0),
(526, 23, 0),
(527, 23, 0),
(528, 23, 0),
(529, 23, 0),
(530, 23, 0),
(531, 23, 0),
(532, 23, 0),
(533, 23, 0),
(534, 23, 0),
(535, 23, 0),
(536, 23, 0),
(537, 23, 0),
(538, 23, 0),
(539, 23, 0),
(540, 23, 0),
(541, 23, 0),
(542, 23, 0),
(543, 23, 0),
(544, 23, 0),
(545, 23, 0),
(546, 23, 0),
(547, 23, 0),
(548, 23, 0),
(549, 23, 0),
(550, 23, 0),
(551, 23, 0),
(552, 23, 0),
(553, 23, 0),
(554, 23, 0),
(555, 23, 1),
(556, 23, 0),
(557, 23, 0),
(558, 23, 0),
(559, 23, 0),
(560, 23, 0),
(561, 23, 0),
(562, 23, 0),
(563, 23, 0),
(564, 23, 0),
(565, 23, 0),
(566, 23, 0),
(567, 23, 0),
(568, 23, 0),
(569, 23, 0),
(570, 23, 0),
(571, 23, 0),
(572, 23, 0),
(573, 23, 0),
(574, 23, 0),
(575, 23, 0),
(576, 23, 0),
(577, 23, 0),
(578, 23, 0),
(579, 23, 0),
(580, 23, 0),
(581, 23, 0),
(582, 23, 0),
(583, 23, 0),
(584, 23, 0),
(585, 23, 0),
(586, 23, 0),
(587, 23, 0),
(588, 23, 0),
(589, 23, 0),
(590, 23, 0),
(591, 23, 0),
(592, 23, 0),
(593, 23, 0),
(594, 23, 0),
(595, 23, 0),
(596, 23, 0),
(597, 23, 0),
(598, 23, 0),
(599, 23, 0),
(600, 23, 0),
(601, 23, 0),
(602, 23, 0),
(603, 23, 0),
(604, 23, 0),
(605, 23, 0),
(606, 23, 0),
(607, 23, 0),
(608, 23, 0),
(609, 23, 0),
(610, 23, 0),
(611, 23, 0),
(612, 23, 0),
(613, 23, 0),
(614, 23, 0),
(615, 23, 0),
(616, 23, 0),
(617, 23, 0),
(618, 23, 0),
(619, 23, 0),
(620, 23, 0),
(621, 23, 0),
(622, 23, 0),
(623, 23, 0),
(624, 23, 0),
(625, 23, 0),
(626, 23, 0),
(627, 23, 0),
(628, 23, 0),
(629, 23, 0),
(630, 23, 0),
(631, 23, 0),
(632, 23, 0),
(633, 23, 0),
(634, 23, 0),
(635, 23, 0),
(636, 23, 0),
(637, 23, 0),
(638, 23, 0),
(639, 23, 0),
(640, 23, 0),
(641, 23, 0),
(642, 23, 0),
(643, 23, 0),
(644, 23, 0),
(645, 23, 0),
(646, 23, 0),
(647, 23, 0),
(648, 23, 0),
(649, 23, 0),
(650, 23, 0),
(651, 23, 0),
(652, 23, 0),
(653, 23, 0),
(654, 23, 0),
(655, 23, 0),
(656, 23, 0),
(657, 23, 0),
(658, 23, 0),
(659, 23, 0),
(660, 23, 0),
(661, 23, 0),
(662, 23, 0),
(663, 23, 0),
(664, 23, 0),
(665, 23, 0),
(666, 23, 0),
(667, 23, 0),
(668, 23, 0),
(669, 23, 0),
(670, 23, 0),
(671, 23, 0),
(672, 23, 0),
(673, 23, 0),
(674, 23, 0),
(675, 23, 0),
(676, 23, 0),
(677, 23, 0),
(678, 23, 0),
(679, 23, 0),
(680, 23, 0),
(681, 23, 0),
(682, 23, 0),
(683, 23, 0),
(684, 23, 0),
(685, 23, 0),
(686, 23, 0),
(687, 23, 0),
(688, 23, 0),
(689, 23, 0),
(690, 23, 0),
(691, 23, 0),
(692, 23, 0),
(693, 23, 0),
(694, 23, 0),
(695, 23, 0),
(696, 23, 0),
(697, 23, 0),
(698, 23, 0),
(699, 23, 0),
(700, 23, 0),
(701, 23, 0),
(702, 23, 0),
(703, 23, 0),
(704, 23, 0),
(705, 23, 0),
(706, 23, 0),
(707, 23, 0),
(708, 23, 0),
(709, 23, 0),
(710, 23, 0),
(711, 23, 0),
(712, 23, 0),
(713, 23, 0),
(714, 23, 0),
(715, 23, 0),
(716, 23, 0),
(717, 23, 0),
(718, 23, 0),
(719, 23, 0),
(720, 23, 0),
(721, 23, 0),
(722, 23, 0),
(723, 23, 0),
(724, 23, 0),
(725, 23, 0),
(726, 23, 0),
(727, 23, 0),
(728, 23, 0),
(729, 23, 0),
(730, 23, 0),
(731, 23, 0),
(732, 23, 0),
(733, 23, 0),
(734, 23, 0),
(735, 23, 0),
(736, 23, 0),
(737, 23, 0),
(738, 23, 0),
(739, 23, 0),
(740, 23, 0),
(741, 23, 0),
(742, 23, 0),
(743, 23, 0),
(744, 23, 0),
(745, 23, 0),
(746, 23, 0),
(747, 23, 0),
(748, 23, 0),
(749, 23, 0),
(750, 23, 0),
(751, 23, 0),
(752, 23, 0),
(753, 23, 0),
(754, 23, 0),
(755, 23, 0),
(756, 23, 0),
(757, 23, 0),
(758, 23, 0),
(759, 23, 0),
(760, 23, 0),
(761, 23, 0),
(762, 23, 0),
(763, 23, 0),
(764, 23, 0),
(765, 23, 0),
(766, 23, 0),
(767, 23, 0),
(768, 23, 0),
(769, 23, 0),
(770, 23, 0),
(771, 23, 0),
(772, 23, 0),
(773, 23, 0),
(774, 23, 0),
(775, 23, 0),
(776, 23, 0),
(777, 23, 0),
(778, 23, 0),
(779, 23, 0),
(780, 23, 0),
(781, 23, 0),
(782, 23, 0),
(783, 23, 0),
(784, 23, 0),
(785, 23, 0),
(786, 23, 0),
(787, 23, 0),
(788, 23, 0),
(789, 23, 0),
(790, 23, 0),
(791, 23, 0),
(792, 23, 0),
(793, 23, 0),
(794, 23, 0),
(795, 23, 0),
(796, 23, 0),
(797, 23, 0),
(798, 23, 0),
(799, 23, 0),
(800, 23, 0),
(801, 23, 0),
(802, 23, 0),
(803, 24, 1),
(804, 24, 0),
(805, 24, 0),
(806, 24, 0),
(807, 24, 0),
(808, 24, 0),
(809, 24, 0),
(810, 24, 0),
(811, 24, 0),
(812, 24, 0),
(813, 24, 0),
(814, 24, 0),
(815, 24, 0),
(816, 24, 0),
(817, 24, 0),
(818, 24, 0),
(819, 24, 0),
(820, 24, 0),
(821, 24, 0),
(822, 24, 0),
(823, 24, 0),
(824, 24, 0),
(825, 24, 0),
(826, 24, 0),
(827, 24, 0),
(828, 24, 0),
(829, 24, 0),
(830, 24, 0),
(831, 24, 0),
(832, 24, 0),
(833, 24, 0),
(834, 24, 0),
(835, 24, 0),
(836, 24, 0),
(837, 24, 0),
(838, 24, 0),
(839, 24, 0),
(840, 24, 0),
(841, 24, 0),
(842, 24, 0),
(843, 24, 0),
(844, 24, 0),
(845, 24, 0),
(846, 24, 0),
(847, 24, 0),
(848, 24, 0),
(849, 24, 0),
(850, 24, 0),
(851, 24, 0),
(852, 24, 0),
(853, 24, 0),
(854, 24, 0),
(855, 24, 0),
(856, 24, 0),
(857, 24, 0),
(858, 24, 0),
(859, 24, 0),
(860, 24, 0),
(861, 24, 0),
(862, 24, 0),
(863, 24, 0),
(864, 24, 0),
(865, 24, 0),
(866, 24, 0),
(867, 24, 0),
(868, 24, 0),
(869, 24, 0),
(870, 24, 0),
(871, 24, 0),
(872, 24, 0),
(873, 24, 0),
(874, 24, 0),
(875, 24, 0),
(876, 24, 0),
(877, 24, 0),
(878, 24, 0),
(879, 24, 0),
(880, 24, 0),
(881, 24, 0),
(882, 24, 0),
(883, 24, 0),
(884, 24, 0),
(885, 24, 0),
(886, 24, 0),
(887, 24, 0),
(888, 24, 0),
(889, 24, 0),
(890, 24, 0),
(891, 24, 0),
(892, 24, 0),
(893, 24, 0),
(894, 24, 0),
(895, 24, 0),
(896, 24, 0),
(897, 24, 0),
(898, 24, 0),
(899, 24, 0),
(900, 24, 0),
(901, 24, 0),
(902, 24, 0),
(903, 24, 0),
(904, 25, 0),
(905, 25, 0),
(906, 25, 1),
(907, 25, 1);

-- --------------------------------------------------------

--
-- Table structure for table `bookinfo`
--
-- Creation: Apr 24, 2018 at 01:26 AM
-- Last update: Apr 25, 2018 at 11:10 AM
--

CREATE TABLE IF NOT EXISTS `bookinfo` (
  `idBook` int(11) NOT NULL AUTO_INCREMENT,
  `Author` varchar(30) NOT NULL,
  `Title` varchar(75) NOT NULL,
  PRIMARY KEY (`idBook`),
  UNIQUE KEY `idBook_UNIQUE` (`idBook`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

--
-- RELATIONS FOR TABLE `bookinfo`:
--

--
-- Dumping data for table `bookinfo`
--

INSERT INTO `bookinfo` (`idBook`, `Author`, `Title`) VALUES
(7, 'J.R.R. Tolkien', 'The Hobbit'),
(8, 'J.R.R. Tolkien', 'The Fellowship of the Ring'),
(15, 'Douglas Adams', 'The Hitchikers Guide to the Galaxy'),
(20, 'J.K.Rowling', 'Harry Potter and the Order of the Phoenix'),
(21, 'Oscar Wilde', 'The Picture of Dorian Gray (Wordsworth Classics)'),
(22, 'Jorge Cham, Daniel Whiteson', 'We Have No Idea: A Guide to the Unknown Universe'),
(23, 'Dante Alighieri', 'The Divine Comedy'),
(24, 'Stephen E. Ambrose', 'Band Of Brothers'),
(25, 'Ian Stewart', 'Professor Stewart\'s cabinet of Mathematical Curiosities');

-- --------------------------------------------------------

--
-- Table structure for table `borrow`
--
-- Creation: Apr 25, 2018 at 03:40 AM
-- Last update: Apr 26, 2018 at 02:12 AM
--

CREATE TABLE IF NOT EXISTS `borrow` (
  `BorrowID` int(11) NOT NULL AUTO_INCREMENT,
  `Date` date NOT NULL,
  `BookID_BookNumberID` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `isStudent` tinyint(1) NOT NULL DEFAULT '0',
  `Returned` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`BorrowID`),
  UNIQUE KEY `BorrowID_UNIQUE` (`BorrowID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

--
-- RELATIONS FOR TABLE `borrow`:
--

--
-- Dumping data for table `borrow`
--

INSERT INTO `borrow` (`BorrowID`, `Date`, `BookID_BookNumberID`, `id`, `isStudent`, `Returned`) VALUES
(1, '2017-05-03', 26, 5, 1, 0),
(2, '2017-05-03', 25, 5, 1, 0),
(3, '2017-05-03', 20, 2, 0, 0),
(4, '2017-05-09', 803, 1, 0, 0),
(5, '2017-05-09', 907, 1, 0, 0),
(6, '2017-05-09', 555, 1, 0, 0),
(7, '2017-05-09', 300, 6, 1, 0),
(8, '2017-05-09', 475, 6, 1, 0),
(9, '2017-05-09', 906, 6, 1, 0),
(10, '2017-05-09', 6, 6, 1, 0),
(11, '2017-05-09', 30, 6, 1, 0),
(12, '2017-05-09', 34, 6, 1, 0),
(13, '2018-04-20', 10, 1, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `date`
--
-- Creation: Apr 24, 2018 at 01:26 AM
-- Last update: Apr 26, 2018 at 02:27 AM
--

CREATE TABLE IF NOT EXISTS `date` (
  `DateChanged` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `DataBaseDate` date NOT NULL,
  PRIMARY KEY (`DateChanged`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- RELATIONS FOR TABLE `date`:
--

--
-- Dumping data for table `date`
--

INSERT INTO `date` (`DateChanged`, `DataBaseDate`) VALUES
('2017-12-05 12:19:13', '2017-12-05'),
('2017-12-05 12:40:17', '2017-12-14'),
('2017-12-05 13:29:48', '2017-10-24'),
('2017-12-05 13:36:34', '2017-10-10'),
('2017-12-05 13:44:22', '2017-10-10'),
('2017-12-05 13:45:37', '2016-05-18'),
('2017-12-06 12:59:43', '2017-05-03'),
('2018-04-25 11:07:18', '2017-05-08'),
('2018-04-25 11:07:39', '2017-05-09'),
('2018-04-25 20:45:02', '2018-04-20'),
('2018-04-25 20:47:08', '2018-04-19'),
('2018-04-25 20:48:01', '2018-04-19'),
('2018-04-25 20:49:04', '2018-04-09'),
('2018-04-25 20:52:39', '2018-04-19'),
('2018-04-25 20:53:46', '2018-04-19'),
('2018-04-25 20:54:31', '2018-04-20'),
('2018-04-26 02:13:22', '2018-04-29'),
('2018-04-26 02:15:00', '2018-04-30'),
('2018-04-26 02:15:19', '2018-05-01'),
('2018-04-26 02:25:35', '2018-05-02'),
('2018-04-26 02:27:26', '2018-05-03');

-- --------------------------------------------------------

--
-- Table structure for table `settings`
--
-- Creation: Apr 24, 2018 at 01:26 AM
-- Last update: Apr 24, 2018 at 11:35 AM
--

CREATE TABLE IF NOT EXISTS `settings` (
  `SettingName` varchar(45) NOT NULL,
  `Value` int(11) NOT NULL,
  PRIMARY KEY (`SettingName`),
  UNIQUE KEY `idSettings_UNIQUE` (`SettingName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- RELATIONS FOR TABLE `settings`:
--

--
-- Dumping data for table `settings`
--

INSERT INTO `settings` (`SettingName`, `Value`) VALUES
('StudentBookBorrowLimit', 6),
('StudentBookDayLimit', 8),
('TeacherBookBorrowLimit', 10),
('TeacherBookDayLimit', 10);

-- --------------------------------------------------------

--
-- Table structure for table `students`
--
-- Creation: Apr 24, 2018 at 01:26 AM
-- Last update: Apr 25, 2018 at 11:08 AM
--

CREATE TABLE IF NOT EXISTS `students` (
  `idStudent` int(11) NOT NULL AUTO_INCREMENT,
  `StudentID` int(4) NOT NULL,
  `Name` varchar(45) DEFAULT NULL,
  `TelephoneNumber` varchar(50) DEFAULT NULL,
  `Active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`idStudent`),
  UNIQUE KEY `idStudents_UNIQUE` (`idStudent`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- RELATIONS FOR TABLE `students`:
--

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`idStudent`, `StudentID`, `Name`, `TelephoneNumber`, `Active`) VALUES
(1, 1001, 'Piet', '0613482899', 1),
(3, 1005, 'bas', '054', 0),
(5, 5678, 'Remco Geuze', '0613482899', 1),
(6, 6284, 'Bas Horselenberg', '06-13457899', 1);

--
-- Triggers `students`
--
DELIMITER $$
CREATE TRIGGER `Students_AFTER_INSERT` AFTER INSERT ON `students` FOR EACH ROW BEGIN
INSERT INTO balancestudents(Students_idStudent) VALUES(NEW.idStudent);
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `teachers`
--
-- Creation: Apr 24, 2018 at 01:26 AM
--

CREATE TABLE IF NOT EXISTS `teachers` (
  `idTeacher` int(11) NOT NULL AUTO_INCREMENT,
  `TeacherID` varchar(3) NOT NULL,
  `Name` varchar(45) DEFAULT NULL,
  `DepartmentName` varchar(45) DEFAULT NULL,
  `Active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`idTeacher`),
  UNIQUE KEY `idTeacher_UNIQUE` (`idTeacher`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- RELATIONS FOR TABLE `teachers`:
--

--
-- Dumping data for table `teachers`
--

INSERT INTO `teachers` (`idTeacher`, `TeacherID`, `Name`, `DepartmentName`, `Active`) VALUES
(1, 'Thi', 'Thies', 'Institute of Engineering', 1),
(2, 'Bry', 'Bryan', 'Institute of Technology', 1);

--
-- Triggers `teachers`
--
DELIMITER $$
CREATE TRIGGER `Teachers_AFTER_INSERT` AFTER INSERT ON `teachers` FOR EACH ROW BEGIN
INSERT INTO balanceteachers(Teachers_idTeacher) VALUES(NEW.idTeacher);
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--
-- Creation: Apr 24, 2018 at 01:59 PM
-- Last update: Apr 24, 2018 at 01:59 PM
--

CREATE TABLE IF NOT EXISTS `transaction` (
  `idTransaction` int(11) NOT NULL AUTO_INCREMENT,
  `idUser` int(11) NOT NULL,
  `Date` date NOT NULL,
  `Change` int(11) NOT NULL,
  `isStudent` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idTransaction`),
  UNIQUE KEY `idTransaction_UNIQUE` (`idTransaction`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- RELATIONS FOR TABLE `transaction`:
--

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`idTransaction`, `idUser`, `Date`, `Change`, `isStudent`) VALUES
(1, 1, '2017-05-03', 13, 1);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `balancestudents`
--
ALTER TABLE `balancestudents`
  ADD CONSTRAINT `fk_BalanceStudents_Students1` FOREIGN KEY (`Students_idStudent`) REFERENCES `students` (`idStudent`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `balanceteachers`
--
ALTER TABLE `balanceteachers`
  ADD CONSTRAINT `fk_BalanceTeachers_Teachers1` FOREIGN KEY (`Teachers_idTeacher`) REFERENCES `teachers` (`idTeacher`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `bookid`
--
ALTER TABLE `bookid`
  ADD CONSTRAINT `fk_BookID_BookInfo` FOREIGN KEY (`BookID`) REFERENCES `bookinfo` (`idBook`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
