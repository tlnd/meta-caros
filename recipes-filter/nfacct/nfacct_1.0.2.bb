SUMMARY = "Tools for managing kernel packet netfilter accounting"
HOMEPAGE = "http://www.netfilter.org/"
BUGTRACKER = "http://bugzilla.netfilter.org/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=8ca43cbc842c2336e835926c2166c28b"

PR = "r0.2"

DEPENDS = "libmnl libnetfilter-acct"
RRECOMMENDS_${PN} = "kernel-module-xt-nfacct"

SRC_URI = "git://git.netfilter.org/nfacct"
SRCREV="${AUTOREV}"

S = "${WORKDIR}/git"

inherit autotools pkgconfig
