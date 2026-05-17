# homiko-system dosyalarini proje kokune kopyalar: ./homiko-system
$ErrorActionPreference = "Stop"
$repoRoot = Resolve-Path (Join-Path $PSScriptRoot "..\..")
$source = $PSScriptRoot
$target = Join-Path $repoRoot "homiko-system"

Write-Host "Kopyalaniyor: $source -> $target"

if (Test-Path $target) {
    Remove-Item -Recurse -Force $target
}

$exclude = @("setup.ps1")
Get-ChildItem -Path $source -Recurse -File | ForEach-Object {
    if ($exclude -contains $_.Name) { return }
    $relative = $_.FullName.Substring($source.Length).TrimStart("\")
    $dest = Join-Path $target $relative
    $destDir = Split-Path $dest -Parent
    if (-not (Test-Path $destDir)) {
        New-Item -ItemType Directory -Path $destDir -Force | Out-Null
    }
    Copy-Item -Path $_.FullName -Destination $dest -Force
}

Write-Host "Tamam: $target"
Write-Host "Sonraki adimlar:"
Write-Host "  cd homiko-system"
Write-Host "  copy .env.example .env"
Write-Host "  docker compose up -d"
