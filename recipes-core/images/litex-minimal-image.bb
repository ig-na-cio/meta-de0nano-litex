SUMMARY = "Minimal test image"
LICENSE = "MIT"

inherit core-image
# inherit image

COMPATIBLE_MACHINE = "de0nano-litex"

# Programas que queremos en nuestro fs
IMAGE_INSTALL = " \
    busybox \
    lua \
    htop \
    devmem2 \
    led-demo \
"

# IMAGE_INSTALL = "busybox"
# PACKAGE_INSTALL = "busybox"

# Formato filesystem
IMAGE_FSTYPES = "cpio.gz"

# Sin features
IMAGE_FEATURES = ""
EXTRA_IMAGE_FEATURES = ""

# Sin lenguajes extra
IMAGE_LINGUAS = ""

# Tamaño mínimo del filesystem
IMAGE_ROOTFS_EXTRA_SPACE = "0"
IMAGE_ROOTFS_MAXSIZE = "65536"

# Sin recomendaciones automáticas
BAD_RECOMMENDATIONS = "*"

IMAGE_FEATURES:remove = "package-management"


# Por alguna razón el init no llega a ejecutarse correctamente
# Hacemos uno básico y manual
ROOTFS_POSTPROCESS_COMMAND += "create_init; "

create_init() {
    mkdir -p ${IMAGE_ROOTFS}/proc
    mkdir -p ${IMAGE_ROOTFS}/sys
    mkdir -p ${IMAGE_ROOTFS}/dev
    mkdir -p ${IMAGE_ROOTFS}/tmp
    
    cat > ${IMAGE_ROOTFS}/init << 'EOF'
#!/bin/sh
mount -t proc proc /proc
mount -t sysfs sysfs /sys
mount -t devtmpfs devtmpfs /dev 2>/dev/null
[ -e /dev/mem ] || mknod /dev/mem c 1 1
clear
echo "SoC ready - Login as root"
exec /sbin/getty -n -l /bin/sh -L 115200 console
EOF
    
    chmod 755 ${IMAGE_ROOTFS}/init
}

# Sin el script corrido hacia la izquierda no anda!
# Probablemente porque estamos copiando el contenido directo en el script.