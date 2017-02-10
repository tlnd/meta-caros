SUMMARY = "Small set of multi-purpose passive network monitoring tools"
HOMEPAGE = "http://www.pmacct.net/"
SECTION = "network"
LICENSE = "GPL-2"
LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=fedab68ad56a92a977db02ed6a40f9e1"

SRC_URI = "git://git@gitlab.tpip.net/tlindhorst/pmacct.git;protocol=ssh;branch=dev"
SRCREV = "29940494cf6612831a1e627d2c0aaf4591a26764"

S = "${WORKDIR}/git"

PR = "r3"

DEPENDS = "libpcap zlib"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit autotools pkgconfig

EXTRA_OECONF = "--with-pcap-includes=${STAGING_INCDIR}"
