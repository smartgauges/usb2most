EESchema Schematic File Version 4
EELAYER 30 0
EELAYER END
$Descr A4 11693 8268
encoding utf-8
Sheet 1 1
Title ""
Date ""
Rev ""
Comp ""
Comment1 ""
Comment2 ""
Comment3 ""
Comment4 ""
$EndDescr
$Comp
L Connector:Conn_01x10_Male J1
U 1 1 5F2BB2E2
P 1750 2450
F 0 "J1" H 1858 3031 50  0000 C CNN
F 1 "Conn_01x10_Male" H 1858 2940 50  0000 C CNN
F 2 "adapter:IL-AG5-10P-S3L2" H 1750 2450 50  0001 C CNN
F 3 "~" H 1750 2450 50  0001 C CNN
	1    1750 2450
	1    0    0    -1  
$EndComp
$Comp
L Connector:Conn_01x10_Male J2
U 1 1 5F2BCAB4
P 4050 2450
F 0 "J2" H 4158 3031 50  0000 C CNN
F 1 "Conn_01x10_Male" H 4158 2940 50  0000 C CNN
F 2 "Connector_PinHeader_2.54mm:PinHeader_1x10_P2.54mm_Vertical" H 4050 2450 50  0001 C CNN
F 3 "~" H 4050 2450 50  0001 C CNN
	1    4050 2450
	-1   0    0    -1  
$EndComp
Wire Wire Line
	1950 2050 2250 2050
Wire Wire Line
	1950 2150 2250 2150
Text GLabel 2250 2050 2    50   Input ~ 0
12V
Text GLabel 2250 2150 2    50   Input ~ 0
MS_H
Text GLabel 2250 2250 2    50   Input ~ 0
MS_L
Text GLabel 2250 2950 2    50   Input ~ 0
GND
Text GLabel 2250 2750 2    50   Input ~ 0
SWC_GND
Text GLabel 2250 2650 2    50   Input ~ 0
SWC
Wire Wire Line
	1950 2950 2250 2950
Text GLabel 1900 4100 2    50   Input ~ 0
ILLUM
Wire Wire Line
	1600 4100 1900 4100
Wire Wire Line
	1600 4000 1900 4000
Text GLabel 1900 4200 2    50   Input ~ 0
BACK
Wire Wire Line
	1600 4200 1900 4200
Text GLabel 1900 4300 2    50   Input ~ 0
AND_TX
Wire Wire Line
	1600 4300 1900 4300
Text GLabel 1900 4400 2    50   Input ~ 0
AND_RX
Wire Wire Line
	1600 4400 1900 4400
Text GLabel 3550 4050 2    50   Input ~ 0
LIN
Wire Wire Line
	3150 4050 3550 4050
Text GLabel 3550 4150 2    50   Input ~ 0
IGN
Wire Wire Line
	3150 4150 3550 4150
Text GLabel 3550 4250 2    50   Input ~ 0
ILLUM
Wire Wire Line
	3150 4250 3550 4250
Text GLabel 3550 4350 2    50   Input ~ 0
BACK
Wire Wire Line
	3150 4350 3550 4350
Text GLabel 2700 6000 2    50   Input ~ 0
HS_H
Wire Wire Line
	2300 6000 2700 6000
Text GLabel 2700 6100 2    50   Input ~ 0
HS_L
Wire Wire Line
	2300 6100 2700 6100
$Comp
L Connector:Conn_01x07_Male J3
U 1 1 5F2D940E
P 1400 4200
F 0 "J3" H 1508 4681 50  0000 C CNN
F 1 "Conn_01x07_Male" H 1508 4590 50  0000 C CNN
F 2 "Connector_PinHeader_2.54mm:PinHeader_1x07_P2.54mm_Vertical" H 1400 4200 50  0001 C CNN
F 3 "~" H 1400 4200 50  0001 C CNN
	1    1400 4200
	1    0    0    -1  
$EndComp
Text GLabel 1900 3900 2    50   Input ~ 0
12V
Wire Wire Line
	1600 3900 1900 3900
Text GLabel 1900 4500 2    50   Input ~ 0
GND
Wire Wire Line
	1600 4500 1900 4500
Text GLabel 1900 4000 2    50   Input ~ 0
IGN
$Comp
L Connector:Conn_01x03_Male J7
U 1 1 5F2F9651
P 3450 5300
F 0 "J7" H 3558 5581 50  0000 C CNN
F 1 "Conn_01x03_Male" H 3558 5490 50  0000 C CNN
F 2 "Connector_PinHeader_2.54mm:PinHeader_1x03_P2.54mm_Vertical" H 3450 5300 50  0001 C CNN
F 3 "~" H 3450 5300 50  0001 C CNN
	1    3450 5300
	1    0    0    -1  
$EndComp
Text GLabel 3950 5200 2    50   Input ~ 0
12V
Wire Wire Line
	3650 5200 3950 5200
Text GLabel 3950 5300 2    50   Input ~ 0
12V
Wire Wire Line
	3650 5300 3950 5300
Text GLabel 3950 5400 2    50   Input ~ 0
12V
Wire Wire Line
	3650 5400 3950 5400
$Comp
L Connector:Conn_01x03_Male J6
U 1 1 5F2FE2E8
P 1850 5300
F 0 "J6" H 1958 5581 50  0000 C CNN
F 1 "Conn_01x03_Male" H 1958 5490 50  0000 C CNN
F 2 "Connector_PinHeader_2.54mm:PinHeader_1x03_P2.54mm_Vertical" H 1850 5300 50  0001 C CNN
F 3 "~" H 1850 5300 50  0001 C CNN
	1    1850 5300
	1    0    0    -1  
$EndComp
Text GLabel 2350 5200 2    50   Input ~ 0
GND
Wire Wire Line
	2050 5200 2350 5200
Text GLabel 2350 5300 2    50   Input ~ 0
GND
Wire Wire Line
	2050 5300 2350 5300
Text GLabel 2350 5400 2    50   Input ~ 0
GND
Wire Wire Line
	2050 5400 2350 5400
$Comp
L Connector:Conn_01x02_Male J8
U 1 1 5F3066E8
P 4900 5300
F 0 "J8" H 5008 5481 50  0000 C CNN
F 1 "Conn_01x02_Male" H 5008 5390 50  0000 C CNN
F 2 "Connector_PinHeader_2.54mm:PinHeader_1x02_P2.54mm_Vertical" H 4900 5300 50  0001 C CNN
F 3 "~" H 4900 5300 50  0001 C CNN
	1    4900 5300
	1    0    0    -1  
$EndComp
Wire Wire Line
	5100 5300 5400 5300
Text GLabel 5400 5300 2    50   Input ~ 0
IGN
Wire Wire Line
	5100 5400 5400 5400
Text GLabel 5400 5400 2    50   Input ~ 0
IGN
$Comp
L Device:R R1
U 1 1 5F31188B
P 6200 3900
F 0 "R1" V 5993 3900 50  0000 C CNN
F 1 "0" V 6084 3900 50  0000 C CNN
F 2 "Resistor_SMD:R_0805_2012Metric" V 6130 3900 50  0001 C CNN
F 3 "~" H 6200 3900 50  0001 C CNN
	1    6200 3900
	0    1    1    0   
$EndComp
Text GLabel 6550 3900 2    50   Input ~ 0
IGN'
Wire Wire Line
	6350 3900 6550 3900
Text GLabel 5850 3900 0    50   Input ~ 0
IGN
Wire Wire Line
	6050 3900 5850 3900
$Comp
L Device:R R2
U 1 1 5F31D917
P 6200 4250
F 0 "R2" V 5993 4250 50  0000 C CNN
F 1 "0" V 6084 4250 50  0000 C CNN
F 2 "Resistor_SMD:R_0805_2012Metric" V 6130 4250 50  0001 C CNN
F 3 "~" H 6200 4250 50  0001 C CNN
	1    6200 4250
	0    1    1    0   
$EndComp
Text GLabel 6550 4250 2    50   Input ~ 0
ILLUM'
Wire Wire Line
	6350 4250 6550 4250
Text GLabel 5850 4250 0    50   Input ~ 0
ILLUM
Wire Wire Line
	6050 4250 5850 4250
$Comp
L Device:R R3
U 1 1 5F31F244
P 6200 4600
F 0 "R3" V 5993 4600 50  0000 C CNN
F 1 "0" V 6084 4600 50  0000 C CNN
F 2 "Resistor_SMD:R_0805_2012Metric" V 6130 4600 50  0001 C CNN
F 3 "~" H 6200 4600 50  0001 C CNN
	1    6200 4600
	0    1    1    0   
$EndComp
Text GLabel 6550 4600 2    50   Input ~ 0
BACK'
Wire Wire Line
	6350 4600 6550 4600
Text GLabel 5850 4600 0    50   Input ~ 0
BACK
Wire Wire Line
	6050 4600 5850 4600
Wire Wire Line
	1950 2250 2250 2250
Wire Wire Line
	1950 2650 2250 2650
Wire Wire Line
	1950 2750 2250 2750
Wire Wire Line
	3850 2050 3550 2050
Wire Wire Line
	3850 2150 3550 2150
Text GLabel 3550 2050 0    50   Input ~ 0
12V
Text GLabel 3550 2150 0    50   Input ~ 0
MS_H
Text GLabel 3550 2250 0    50   Input ~ 0
MS_L
Wire Wire Line
	3850 2250 3550 2250
Text GLabel 3550 2950 0    50   Input ~ 0
GND
Wire Wire Line
	3850 2950 3550 2950
Wire Wire Line
	3850 2650 3550 2650
Wire Wire Line
	3850 2750 3550 2750
Text GLabel 3550 2650 0    50   Input ~ 0
SWC
Text GLabel 3550 2750 0    50   Input ~ 0
SWC_GND
Wire Wire Line
	4500 4500 4900 4500
Text GLabel 4900 4500 2    50   Input ~ 0
HS_L
Wire Wire Line
	4500 4400 4900 4400
Text GLabel 4900 4400 2    50   Input ~ 0
HS_H
Wire Wire Line
	4500 4300 4900 4300
Text GLabel 4900 4300 2    50   Input ~ 0
AND_RX
Wire Wire Line
	4500 4200 4900 4200
Text GLabel 4900 4200 2    50   Input ~ 0
AND_TX
Wire Wire Line
	4500 4100 4900 4100
Text GLabel 4900 4100 2    50   Input ~ 0
BACK'
Wire Wire Line
	4500 4000 4900 4000
Text GLabel 4900 4000 2    50   Input ~ 0
ILLUM'
Wire Wire Line
	4500 3900 4900 3900
Text GLabel 4900 3900 2    50   Input ~ 0
IGN'
Wire Wire Line
	4500 3800 4900 3800
Text GLabel 4900 3800 2    50   Input ~ 0
LIN
$Comp
L Connector:Conn_01x04_Male J9
U 1 1 5F3646C9
P 2100 6100
F 0 "J9" H 2208 6381 50  0000 C CNN
F 1 "Conn_01x04_Male" H 2208 6290 50  0000 C CNN
F 2 "Connector_PinHeader_2.54mm:PinHeader_1x04_P2.54mm_Vertical" H 2100 6100 50  0001 C CNN
F 3 "~" H 2100 6100 50  0001 C CNN
	1    2100 6100
	1    0    0    -1  
$EndComp
Text GLabel 2700 6200 2    50   Input ~ 0
KEY_H
Wire Wire Line
	2300 6200 2700 6200
Text GLabel 2700 6300 2    50   Input ~ 0
KEY_L
Wire Wire Line
	2300 6300 2700 6300
$Comp
L Connector:Conn_01x04_Male J4
U 1 1 5F36E53C
P 2950 4150
F 0 "J4" H 3058 4431 50  0000 C CNN
F 1 "Conn_01x04_Male" H 3058 4340 50  0000 C CNN
F 2 "Connector_PinHeader_2.54mm:PinHeader_1x04_P2.54mm_Vertical" H 2950 4150 50  0001 C CNN
F 3 "~" H 2950 4150 50  0001 C CNN
	1    2950 4150
	1    0    0    -1  
$EndComp
$Comp
L Connector:Conn_01x10_Male J5
U 1 1 5F379CD3
P 4300 4200
F 0 "J5" H 4408 4781 50  0000 C CNN
F 1 "Conn_01x10_Male" H 4408 4690 50  0000 C CNN
F 2 "Connector_PinHeader_2.54mm:PinHeader_1x10_P2.54mm_Vertical" H 4300 4200 50  0001 C CNN
F 3 "~" H 4300 4200 50  0001 C CNN
	1    4300 4200
	1    0    0    -1  
$EndComp
Wire Wire Line
	4500 4700 4900 4700
Text GLabel 4900 4700 2    50   Input ~ 0
KEY_L
Wire Wire Line
	4500 4600 4900 4600
Text GLabel 4900 4600 2    50   Input ~ 0
KEY_H
$Comp
L Connector:Conn_01x02_Male J10
U 1 1 5F2C4A78
P 4850 5900
F 0 "J10" H 4958 6081 50  0000 C CNN
F 1 "Conn_01x02_Male" H 4958 5990 50  0000 C CNN
F 2 "Connector_PinHeader_2.54mm:PinHeader_1x02_P2.54mm_Vertical" H 4850 5900 50  0001 C CNN
F 3 "~" H 4850 5900 50  0001 C CNN
	1    4850 5900
	1    0    0    -1  
$EndComp
Wire Wire Line
	5050 5900 5350 5900
Text GLabel 5350 5900 2    50   Input ~ 0
ILLUM
Wire Wire Line
	5050 6000 5350 6000
Text GLabel 5350 6000 2    50   Input ~ 0
ILLUM
$EndSCHEMATC
