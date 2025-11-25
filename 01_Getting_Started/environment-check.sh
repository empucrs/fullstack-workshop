#!/bin/bash

# Fullstack Workshop - Environment Check Script
# This script verifies that all required tools are installed

echo "======================================"
echo "Fullstack Workshop Environment Check"
echo "======================================"
echo ""

# Color codes
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Counters
PASSED=0
FAILED=0

# Function to check command
check_command() {
    local cmd=$1
    local name=$2
    local version_cmd=$3

    echo -n "Checking $name... "

    if command -v $cmd &> /dev/null; then
        version=$($version_cmd 2>&1 | head -n 1)
        echo -e "${GREEN}✓ FOUND${NC}"
        echo "  Version: $version"
        ((PASSED++))
    else
        echo -e "${RED}✗ NOT FOUND${NC}"
        echo "  Please install $name"
        ((FAILED++))
    fi
    echo ""
}

# Check Git
check_command "git" "Git" "git --version"

# Check Java
echo -n "Checking Java... "
if command -v java &> /dev/null; then
    version=$(java -version 2>&1 | head -n 1)
    echo -e "${GREEN}✓ FOUND${NC}"
    echo "  $version"

    # Check Java version is 17+
    java_version=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
    if [ "$java_version" -ge 17 ]; then
        echo -e "  ${GREEN}Version OK (17+)${NC}"
    else
        echo -e "  ${YELLOW}Warning: Java 17+ recommended${NC}"
    fi
    ((PASSED++))
else
    echo -e "${RED}✗ NOT FOUND${NC}"
    echo "  Please install Java JDK 17 or higher"
    ((FAILED++))
fi
echo ""

# Check Maven
echo -n "Checking Maven... "
if command -v mvn &> /dev/null; then
    version=$(mvn -version 2>&1 | head -n 1)
    echo -e "${GREEN}✓ FOUND${NC}"
    echo "  $version"
    ((PASSED++))
else
    echo -e "${YELLOW}⚠ NOT FOUND${NC}"
    echo "  Maven wrapper (mvnw) can be used instead"
fi
echo ""

# Check Node.js
echo -n "Checking Node.js... "
if command -v node &> /dev/null; then
    version=$(node --version)
    echo -e "${GREEN}✓ FOUND${NC}"
    echo "  Version: $version"

    # Check Node version is 18+
    node_version=$(node --version | cut -d'v' -f2 | cut -d'.' -f1)
    if [ "$node_version" -ge 18 ]; then
        echo -e "  ${GREEN}Version OK (18+)${NC}"
    else
        echo -e "  ${YELLOW}Warning: Node.js 18+ recommended${NC}"
    fi
    ((PASSED++))
else
    echo -e "${RED}✗ NOT FOUND${NC}"
    echo "  Please install Node.js 18 or higher"
    ((FAILED++))
fi
echo ""

# Check npm
check_command "npm" "npm" "npm --version"

# Summary
echo "======================================"
echo "Summary"
echo "======================================"
echo -e "${GREEN}Passed: $PASSED${NC}"
if [ $FAILED -gt 0 ]; then
    echo -e "${RED}Failed: $FAILED${NC}"
fi
echo ""

if [ $FAILED -eq 0 ]; then
    echo -e "${GREEN}✓ All required tools are installed!${NC}"
    echo "You're ready to start the workshop."
    exit 0
else
    echo -e "${RED}✗ Some required tools are missing.${NC}"
    echo "Please install the missing tools and run this script again."
    exit 1
fi

