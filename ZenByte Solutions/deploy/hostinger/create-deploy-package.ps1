# Creates zenbyte-deploy.zip for Hostinger File Manager upload
# Run: .\deploy\hostinger\create-deploy-package.ps1

$ErrorActionPreference = "Stop"
$Root = Split-Path (Split-Path $PSScriptRoot -Parent) -Parent
$OutZip = Join-Path $Root "zenbyte-deploy.zip"
$TempDir = Join-Path $env:TEMP "zenbyte-deploy-$(Get-Random)"

Write-Host "Creating deployment package..."

New-Item -ItemType Directory -Path $TempDir -Force | Out-Null

$items = @(
    "frontend",
    "backend",
    "nodejs",
    "database",
    "deploy",
    "docker-compose.prod.yml",
    "docker-compose.yml",
    "package.json",
    "HOSTINGER_DEPLOY.md"
)

foreach ($item in $items) {
    $src = Join-Path $Root $item
    if (Test-Path $src) {
        Copy-Item -Path $src -Destination (Join-Path $TempDir $item) -Recurse -Force
    }
}

# Exclude build artifacts
$targetDir = Join-Path $TempDir "backend\target"
if (Test-Path $targetDir) { Remove-Item $targetDir -Recurse -Force }

if (Test-Path $OutZip) { Remove-Item $OutZip -Force }
Compress-Archive -Path "$TempDir\*" -DestinationPath $OutZip -Force
Remove-Item $TempDir -Recurse -Force

Write-Host ""
Write-Host "Package created: $OutZip"
Write-Host ""
Write-Host "NEXT STEPS:"
Write-Host "  1. Hostinger hPanel -> VPS -> File Manager (or SFTP)"
Write-Host "  2. Upload zenbyte-deploy.zip to /var/www/"
Write-Host "  3. Extract to /var/www/zenbyte"
Write-Host "  4. Open Browser Terminal and run:"
Write-Host "     cd /var/www/zenbyte"
Write-Host "     chmod +x deploy/hostinger/one-click-deploy.sh"
Write-Host "     ./deploy/hostinger/one-click-deploy.sh"
