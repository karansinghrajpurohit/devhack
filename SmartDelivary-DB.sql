-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Oct 20, 2019 at 01:59 PM
-- Server version: 5.6.35
-- PHP Version: 7.0.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `smart_delivery_app`
--
CREATE DATABASE IF NOT EXISTS `smart_delivery_app` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `smart_delivery_app`;

-- --------------------------------------------------------

--
-- Table structure for table `cust_reg_login`
--

CREATE TABLE `cust_reg_login` (
  `customer_id` int(11) UNSIGNED NOT NULL,
  `cust_name` varchar(50) DEFAULT NULL,
  `cust_last_name` varchar(50) DEFAULT NULL,
  `cust_password` varchar(50) DEFAULT NULL,
  `cust_email` varchar(50) DEFAULT NULL,
  `cust_mobile` varchar(13) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cust_reg_login`
--

INSERT INTO `cust_reg_login` (`customer_id`, `cust_name`, `cust_last_name`, `cust_password`, `cust_email`, `cust_mobile`) VALUES
(1, 'Anirudh', 'Huilgol', 'aabbcc', 'aa@bb.com', '8861899697'),
(2, 'Pavan', 'Bhat', 'abc@1234', 'abc@gmail.com', '9980123470');

-- --------------------------------------------------------

--
-- Table structure for table `delivary_boy_reg_login`
--

CREATE TABLE `delivary_boy_reg_login` (
  `delevary_boy_id` int(11) UNSIGNED NOT NULL,
  `person_name` varchar(50) DEFAULT NULL,
  `person_last_name` varchar(50) DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `user_password` varchar(50) DEFAULT NULL,
  `delivary_area` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `delivary_boy_reg_login`
--

INSERT INTO `delivary_boy_reg_login` (`delevary_boy_id`, `person_name`, `person_last_name`, `user_name`, `user_password`, `delivary_area`) VALUES
(1, 'Ramu', 'Kaka', 'Ramu', 'RamuKaka@1234', 'Vidyanagar'),
(2, 'Shamu', 'Kaka', 'Shamu', 'Shamu@1234', 'Sainagar'),
(3, 'Karthik', 'M', 'Karthik', 'abc@123', 'Airport Road Hubli');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `order_id` int(11) UNSIGNED NOT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `total_cost` varchar(50) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  `Qty` int(11) DEFAULT NULL,
  `delivary_address` varchar(255) DEFAULT NULL,
  `is_delivared` int(2) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`order_id`, `customer_id`, `total_cost`, `item_id`, `Qty`, `delivary_address`, `is_delivared`) VALUES
(1, 1, '105000', 1, 1, 'vidyanagar Hubli', 1),
(2, 1, '105000', 1, 1, 'SAInagar Hubli', 1),
(3, 1, ' 80374 ', 2, 1, 'sainagar,Hubli', 1),
(4, 1, ' 105000 ', 1, 1, 'SAInagar Hubli', 1),
(5, 1, ' 200000 ', 5, 1, 'H.no 168 near Hanuman Temple murarji nagar airport road Hubli', 1),
(6, 1, ' 105000 ', 1, 1, 'Sushruta hospital vidyanagar Hubli', 1),
(7, 1, ' 14999 ', 3, 1, 'kle cim canteen vidyanagar hubli', 1),
(8, 1, ' 200000 ', 5, 1, 'tata motors vidyanagar Hubli', 1),
(9, 1, ' 105000 ', 1, 1, 'HP petrol pump airport road hubli', 0),
(10, 1, ' 200000 ', 5, 1, 'big Bazar airport road Hubli', 0),
(11, 1, ' 80374 ', 2, 1, 'vatsalya hospital airport road Hubli', 0),
(12, 1, ' 200000 ', 5, 1, 'Ayyappa swamy temple shirur park vidyanagar Hubli', 0),
(13, 1, '80000', 1, 1, 'hno 168,ashirwad nilaya murarji nagar airport road hubli', 0),
(14, 1, '80000', 1, 1, 'KEC Airport road hubli', 0),
(15, 1, '80000', 1, 1, 'KLE IT college Airport road hubli', 0),
(16, 1, '80000', 1, 1, 'KEC Bus stop Airport road hubli', 0);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `product_id` int(11) UNSIGNED NOT NULL,
  `product_name` varchar(50) DEFAULT NULL,
  `price` varchar(11) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`product_id`, `product_name`, `price`, `image`) VALUES
(1, 'iPhone X', '105000', 'https://ss7.vzw.com/is/image/VerizonWireless/iphone-x-kf-device-tab-d-1-hero?$pngalpha$'),
(2, 'iPhone 8 Plus', '80374', 'https://img.tatacliq.com/images/i2/437Wx649H/MP000000001838240_437Wx649H_20170921131545.jpeg'),
(3, 'Moto g5s plus', '14999', 'https://drop.ndtv.com/TECH/product_database/images/moto_g5splus_blackface_4262_480X960_915201735615PM.jpg'),
(5, 'godrej', '200000', 'https://md5.pricebaba.com/images/product/refrigerators/9307/godrej-190-litre-single-door-refrigerator-rd-edgepro-190-pds-5-2-price-india-xxl.jpg');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cust_reg_login`
--
ALTER TABLE `cust_reg_login`
  ADD PRIMARY KEY (`customer_id`);

--
-- Indexes for table `delivary_boy_reg_login`
--
ALTER TABLE `delivary_boy_reg_login`
  ADD PRIMARY KEY (`delevary_boy_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`product_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cust_reg_login`
--
ALTER TABLE `cust_reg_login`
  MODIFY `customer_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `delivary_boy_reg_login`
--
ALTER TABLE `delivary_boy_reg_login`
  MODIFY `delevary_boy_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `order_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `product_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
