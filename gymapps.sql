-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 01, 2023 at 03:58 PM
-- Server version: 10.3.15-MariaDB
-- PHP Version: 7.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gymapps`
--

-- --------------------------------------------------------

--
-- Table structure for table `instruktur_gym`
--

CREATE TABLE `instruktur_gym` (
  `id_instruktur` varchar(25) NOT NULL,
  `nama_instruktur` varchar(100) NOT NULL,
  `usia` int(100) NOT NULL,
  `gender` varchar(100) NOT NULL,
  `alamat` varchar(1000) NOT NULL,
  `no_telp` varchar(15) NOT NULL,
  `email` varchar(100) NOT NULL,
  `tanggal_join` date NOT NULL,
  `aktif` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `instruktur_gym`
--

INSERT INTO `instruktur_gym` (`id_instruktur`, `nama_instruktur`, `usia`, `gender`, `alamat`, `no_telp`, `email`, `tanggal_join`, `aktif`) VALUES
('TRA-0001', 'ismail bin abdul jalil', 29, 'Laki-laki', 'qatar', '088888888888', 'a@a.a', '2022-11-15', 'aktif'),
('TRA-0002', 'ADI', 99, 'Laki-laki', 'DEPOK', '021000000', 'a@a.a', '2022-12-12', 'aktif');

-- --------------------------------------------------------

--
-- Table structure for table `instruktur_harga`
--

CREATE TABLE `instruktur_harga` (
  `id` int(11) NOT NULL,
  `harga` bigint(20) NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `instruktur_harga`
--

INSERT INTO `instruktur_harga` (`id`, `harga`, `date`) VALUES
(1, 200000, '2022-11-22 09:14:48'),
(2, 250000, '2022-11-22 09:16:06'),
(3, 260000, '2022-11-22 09:17:34');

-- --------------------------------------------------------

--
-- Table structure for table `jadwal_kelas`
--

CREATE TABLE `jadwal_kelas` (
  `id` varchar(25) NOT NULL,
  `id_kelas` varchar(25) NOT NULL,
  `tanggal` date NOT NULL,
  `mulai` time NOT NULL,
  `selesai` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jadwal_kelas`
--

INSERT INTO `jadwal_kelas` (`id`, `id_kelas`, `tanggal`, `mulai`, `selesai`) VALUES
('CLS202211010', 'POUND', '2022-11-27', '20:03:00', '20:03:00'),
('CLS202211011', 'ZUM', '2022-11-29', '22:13:00', '22:13:00'),
('CLS202212001', 'YOG', '2022-12-17', '15:53:00', '19:53:00');

-- --------------------------------------------------------

--
-- Table structure for table `kelas_gym`
--

CREATE TABLE `kelas_gym` (
  `id_kelas` varchar(25) NOT NULL,
  `nama_kelas` varchar(100) NOT NULL,
  `id_instruktur` varchar(25) NOT NULL,
  `harga_sesi` bigint(20) NOT NULL,
  `keterangan` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `kelas_gym`
--

INSERT INTO `kelas_gym` (`id_kelas`, `nama_kelas`, `id_instruktur`, `harga_sesi`, `keterangan`) VALUES
('-', 'Tidak Ada Sesi Kelas', '-', 0, '-'),
('POUND', 'Poundfit sunday class', 'TRA-0001', 50000, 'poundift'),
('YOG', 'YOGA ', 'TRA-0002', 25000, 'kelas yoga pagi'),
('ZUM', 'ZUMBA CLASS SUNDAY', 'TRA-0001', 20000, 'kelas zumba oleh pak ismail');

-- --------------------------------------------------------

--
-- Table structure for table `logs_absensi_kelas`
--

CREATE TABLE `logs_absensi_kelas` (
  `id_jadwal` varchar(25) NOT NULL,
  `id_member` varchar(25) NOT NULL,
  `time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `logs_absensi_kelas`
--

INSERT INTO `logs_absensi_kelas` (`id_jadwal`, `id_member`, `time`) VALUES
('MEM-0001', 'MEM-0001', '2022-11-26 15:21:36'),
('MEM-0001', 'MEM-0001', '2022-11-26 15:23:40'),
('POUND', 'MEM-0001', '2022-11-26 15:25:06'),
('CLS202211010', 'MEM-0001', '2022-11-26 15:28:48'),
('CLS202211010', 'MEM-0001', '2022-12-11 12:55:53'),
('CLS202212001', 'MEM-0003', '2022-12-11 13:00:36');

-- --------------------------------------------------------

--
-- Table structure for table `logs_checkin`
--

CREATE TABLE `logs_checkin` (
  `id_user` varchar(25) NOT NULL,
  `trainer` varchar(10) NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `logs_checkin`
--

INSERT INTO `logs_checkin` (`id_user`, `trainer`, `date`) VALUES
('MEM-0001', '', '2022-11-26 10:03:27'),
('MEM-0001', '', '2022-12-04 06:29:32'),
('MEM-0001', '', '2022-12-04 06:29:33'),
('MEM-0001', '', '2022-12-04 06:29:41'),
('MEM-0001', '', '2022-12-04 06:31:21'),
('MEM-0001', '', '2022-12-04 06:31:32'),
('MEM-0001', '', '2022-12-04 06:31:33'),
('MEM-0001', '', '2022-12-04 06:31:34'),
('MEM-0001', '', '2022-12-04 06:31:34'),
('MEM-0001', '', '2022-12-04 06:31:44'),
('MEM-0001', '', '2022-12-04 06:31:44'),
('MEM-0001', '', '2022-12-04 06:40:55'),
('MEM-0002', '', '2022-12-04 06:49:02'),
('MEM-0002', '', '2022-12-04 06:49:18'),
('MEM-0002', '', '2022-12-04 06:49:19'),
('MEM-0002', '', '2022-12-04 06:49:19'),
('MEM-0002', '', '2022-12-04 06:49:19'),
('MEM-0002', '', '2022-12-04 06:49:20'),
('MEM-0002', '', '2022-12-04 06:49:31'),
('MEM-0002', '', '2022-12-04 06:57:17'),
('MEM-0002', '', '2022-12-04 06:57:32'),
('MEM-0002', '', '2022-12-04 06:57:33'),
('MEM-0002', '', '2022-12-04 06:57:35'),
('MEM-0002', '', '2022-12-04 06:57:35'),
('MEM-0002', '', '2022-12-04 06:57:35'),
('MEM-0002', '', '2022-12-04 06:57:36'),
('MEM-0002', '', '2022-12-04 06:57:36'),
('MEM-0002', '', '2022-12-04 06:57:36'),
('MEM-0002', '', '2022-12-04 06:57:36'),
('MEM-0002', '', '2022-12-04 06:57:36'),
('MEM-0002', '', '2022-12-04 06:57:36'),
('MEM-0002', '', '2022-12-04 06:57:38'),
('MEM-0002', '', '2022-12-04 06:57:56'),
('MEM-0002', '', '2022-12-04 06:58:07'),
('MEM-0002', '', '2022-12-04 06:58:08'),
('MEM-0002', '', '2022-12-04 06:58:20'),
('MEM-0002', '', '2022-12-04 07:00:01'),
('MEM-0001', '', '2022-12-04 07:00:29'),
('MEM-0001', '', '2022-12-04 07:00:35'),
('MEM-0001', '', '2022-12-04 07:14:52'),
('MEM-0002', 'Tidak', '2022-12-04 07:37:28'),
('MEM-0002', 'Iya', '2022-12-04 07:37:31'),
('MEM-0001', 'Tidak', '2022-12-04 07:37:37'),
('MEM-0001', 'Iya', '2022-12-04 07:37:39'),
('MEM-0001', 'Iya', '2022-12-04 07:38:10'),
('MEM-0003', 'Tidak', '2022-12-11 13:04:41'),
('MEM-0003', 'Iya', '2022-12-12 11:31:27');

-- --------------------------------------------------------

--
-- Table structure for table `logs_kelas`
--

CREATE TABLE `logs_kelas` (
  `id_member` varchar(25) NOT NULL,
  `id_kelas` varchar(25) NOT NULL,
  `id_membership` varchar(25) NOT NULL,
  `jumlah` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `logs_kelas`
--

INSERT INTO `logs_kelas` (`id_member`, `id_kelas`, `id_membership`, `jumlah`) VALUES
('MEM-0001', 'POUND', 'FIT202211009', 2),
('MEM-0002', 'ZUM', 'FIT202212001', 2),
('MEM-0002', 'ZUM', 'FIT202212002', 5),
('MEM-0003', 'YOG', 'FIT202212003', 6),
('', 'POUND', 'FIT202212006', 1),
('MEM-0001', 'YOG', 'FIT202212007', 1),
('-', '-', 'FIT202212008', 0),
('-', '-', 'FIT202212011', 0),
('-', '-', 'FIT202212012', 0);

-- --------------------------------------------------------

--
-- Table structure for table `logs_membership`
--

CREATE TABLE `logs_membership` (
  `id` varchar(25) NOT NULL,
  `id_member` varchar(25) NOT NULL,
  `id_tipe_membership` varchar(25) NOT NULL,
  `mulai` date NOT NULL,
  `akhir` date NOT NULL,
  `id_trainer` varchar(25) NOT NULL,
  `sesi` int(11) NOT NULL,
  `biaya_membership` bigint(20) NOT NULL,
  `biaya_trainer` bigint(20) NOT NULL,
  `biaya_kelas` bigint(20) NOT NULL,
  `total` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `logs_membership`
--

INSERT INTO `logs_membership` (`id`, `id_member`, `id_tipe_membership`, `mulai`, `akhir`, `id_trainer`, `sesi`, `biaya_membership`, `biaya_trainer`, `biaya_kelas`, `total`) VALUES
('FIT202212002', 'MEM-0002', 'GOL', '2022-12-03', '2023-11-30', 'TRA-0001', 12, 1000000, 3120000, 100000, 4220000),
('FIT202212003', 'MEM-0003', 'SIL', '2022-12-11', '2023-06-09', 'TRA-0002', 6, 500000, 1560000, 150000, 2210000),
('FIT202212004', 'MEM-0001', 'SIL', '2022-12-12', '2023-06-10', '-', 0, 500000, 0, 0, 500000),
('FIT202212005', 'MEM-0001', 'GOL', '2022-12-12', '2023-12-07', '', 0, 1000000, 0, 0, 1000000),
('FIT202212006', 'MEM-0001', 'GOL', '2022-12-04', '2023-11-29', '-', 0, 1000000, 0, 50000, 1050000),
('FIT202212007', 'MEM-0001', 'SIL', '2022-12-12', '2023-06-10', 'TRA-0001', 1, 500000, 260000, 25000, 785000),
('FIT202212008', 'MEM-0001', 'GOL', '2022-12-12', '2023-12-07', 'TRA-0002', 1, 1000000, 260000, 0, 1260000),
('FIT202212009', 'MEM-0001', 'GOL', '2022-12-12', '2023-12-07', '-', 0, 1000000, 0, 0, 1000000),
('FIT202212010', 'MEM-0002', 'SIL', '2022-12-12', '2023-06-10', '-', 0, 500000, 0, 0, 500000),
('FIT202212011', 'MEM-0003', 'SIL', '2022-12-12', '2023-06-10', '-', 0, 500000, 0, 0, 500000),
('FIT202212012', 'MEM-0003', 'SIL', '2022-12-12', '2023-06-10', '-', 0, 500000, 0, 0, 500000);

-- --------------------------------------------------------

--
-- Table structure for table `logs_sesi_trainer`
--

CREATE TABLE `logs_sesi_trainer` (
  `id_member` varchar(25) NOT NULL,
  `sesi` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `logs_sesi_trainer`
--

INSERT INTO `logs_sesi_trainer` (`id_member`, `sesi`) VALUES
('MEM-0002', 4),
('MEM-0003', 5),
('MEM-0001', 3);

-- --------------------------------------------------------

--
-- Table structure for table `logs_transaksi`
--

CREATE TABLE `logs_transaksi` (
  `id_transaksi` varchar(25) NOT NULL,
  `id_member` varchar(25) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `total` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `logs_transaksi`
--

INSERT INTO `logs_transaksi` (`id_transaksi`, `id_member`, `timestamp`, `total`) VALUES
('TRANS202211001', 'MEM-0001', '2022-12-02 03:01:44', 760000),
('TRANS202211002', 'MEM-0001', '2022-11-27 10:04:12', 300000),
('TRANS202211003', 'MEM-0001', '2022-12-02 03:02:04', 300000),
('TRANS202212001', 'MEM-0002', '2022-12-05 04:37:21', 280000),
('TRANS202212002', 'MEM-0003', '2022-12-11 13:14:20', 325000),
('TRANS202212003', 'MEM-0003', '2022-12-13 09:02:52', 250000);

-- --------------------------------------------------------

--
-- Table structure for table `logs_transaksi_detail`
--

CREATE TABLE `logs_transaksi_detail` (
  `id_transaksi` varchar(25) NOT NULL,
  `id_barang` varchar(25) NOT NULL,
  `kuantitas` int(11) NOT NULL,
  `total` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `logs_transaksi_detail`
--

INSERT INTO `logs_transaksi_detail` (`id_transaksi`, `id_barang`, `kuantitas`, `total`) VALUES
('TRANS202211001', 'PRO-0001', 4, 600000),
('TRANS202211001', 'PRO-0002', 2, 160000),
('TRANS202211002', 'PRO-0001', 2, 300000),
('TRANS202211003', 'PRO-0001', 2, 300000),
('TRANS202212001', 'PRO-0003', 2, 200000),
('TRANS202212001', 'PRO-0002', 1, 80000),
('TRANS202212002', 'PRO-0005', 1, 200000),
('TRANS202212002', 'PRO-0004', 1, 125000),
('TRANS202212003', 'PRO-0004', 2, 250000);

-- --------------------------------------------------------

--
-- Table structure for table `member_gym`
--

CREATE TABLE `member_gym` (
  `id_member` varchar(225) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `usia` int(100) NOT NULL,
  `gender` varchar(100) NOT NULL,
  `alamat` varchar(1000) NOT NULL,
  `no_telp` varchar(15) NOT NULL,
  `email` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `member_gym`
--

INSERT INTO `member_gym` (`id_member`, `nama`, `usia`, `gender`, `alamat`, `no_telp`, `email`) VALUES
('MEM-0001', 'Desiyanti N.', 24, 'Perempuan', 'depok', '08888888888', 'a@a.com'),
('MEM-0002', 'nopal', 99, 'Laki-laki', 'DEPOK', '088888888888', 'a@a.a'),
('MEM-0003', 'ADI', 99, 'Laki-laki', 'depok', '021000000', 'a@a.a');

-- --------------------------------------------------------

--
-- Table structure for table `produk`
--

CREATE TABLE `produk` (
  `id_produk` varchar(200) NOT NULL,
  `nama_produk` varchar(100) NOT NULL,
  `jenis_produk` varchar(100) NOT NULL,
  `harga_produk` bigint(100) NOT NULL,
  `stok` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `produk`
--

INSERT INTO `produk` (`id_produk`, `nama_produk`, `jenis_produk`, `harga_produk`, `stok`) VALUES
('PRO-0001', 'Tshit jersey setu fitness', 'pakaian', 150000, 5),
('PRO-0002', 'Celana jersey setu fitness', 'pakaian', 80000, 5),
('PRO-0003', 'Tumbler 750ml', 'merchandise', 100000, 8),
('PRO-0004', 'Tumbler 1000ml', 'merchandise', 125000, 7),
('PRO-0005', 'SUSU PROTEIN 2KG', 'SUPLEMEN', 200000, 9);

-- --------------------------------------------------------

--
-- Table structure for table `tipe_membership`
--

CREATE TABLE `tipe_membership` (
  `id_tipe_membership` varchar(25) NOT NULL,
  `nama_membership` varchar(100) NOT NULL,
  `harga_membership` bigint(20) NOT NULL,
  `durasi` int(11) NOT NULL,
  `keterangan` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tipe_membership`
--

INSERT INTO `tipe_membership` (`id_tipe_membership`, `nama_membership`, `harga_membership`, `durasi`, `keterangan`) VALUES
('GOL', 'GOLD', 1000000, 360, 'Membership selama 1 tahun lamanya'),
('SIL', 'SILVER MEMBERSHIP', 500000, 180, '6 BULAN');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id_user` varchar(100) NOT NULL,
  `nama_user` varchar(1000) NOT NULL,
  `password_user` varchar(100) CHARACTER SET latin1 NOT NULL,
  `alamat` varchar(250) NOT NULL,
  `no_telp` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `tipe_user` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id_user`, `nama_user`, `password_user`, `alamat`, `no_telp`, `email`, `tipe_user`) VALUES
('doe', 'doeflamingo', 'øL¼¯}%óEEÃÔî1', 'depok', '08588888888', 'doe@gmail.com', 'STAFF'),
('nfl', 'naufal', 'øL¼¯}%óEEÃÔî1', 'depok', '088888888888', 'aditya21naufal@gmail.com', 'STAFF');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `instruktur_gym`
--
ALTER TABLE `instruktur_gym`
  ADD PRIMARY KEY (`id_instruktur`);

--
-- Indexes for table `instruktur_harga`
--
ALTER TABLE `instruktur_harga`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `jadwal_kelas`
--
ALTER TABLE `jadwal_kelas`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `kelas_gym`
--
ALTER TABLE `kelas_gym`
  ADD PRIMARY KEY (`id_kelas`);

--
-- Indexes for table `logs_membership`
--
ALTER TABLE `logs_membership`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `logs_transaksi`
--
ALTER TABLE `logs_transaksi`
  ADD PRIMARY KEY (`id_transaksi`);

--
-- Indexes for table `member_gym`
--
ALTER TABLE `member_gym`
  ADD PRIMARY KEY (`id_member`);

--
-- Indexes for table `produk`
--
ALTER TABLE `produk`
  ADD PRIMARY KEY (`id_produk`);

--
-- Indexes for table `tipe_membership`
--
ALTER TABLE `tipe_membership`
  ADD PRIMARY KEY (`id_tipe_membership`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `instruktur_harga`
--
ALTER TABLE `instruktur_harga`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
