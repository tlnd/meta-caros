SUMMARY = "Command line interface to hello service"
HOMEPAGE = "http://github.com/mgumz/chello/"
SECTION = "utils"
LICENSE = "CLOSED"

PR = "r0.1"

S = "${WORKDIR}/git"

DEPENDS += "golang-cross"
RDEPENDS_${PN} += "zeromq"
SRC_URI = "git://github.com/mgumz/chello.git"
SRCREV = "${AUTOREV}"

export GOPATH = "${WORKDIR}/go"


do_configure() {
	mkdir -p ${GOPATH}
	go get -v -d
}

do_compile() {
	go build -v -o ${PN}
}

do_install() {
	install -d ${D}/${bindir}
	install -m 755 -t ${D}/${bindir} ${S}/${PN} 
}
