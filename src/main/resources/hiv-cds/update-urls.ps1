clear
echo "Adding .json suffix..."
get-childitem ./ -recurse -include *.json | 
  select -expand fullname |
    foreach {
            (Get-Content $_) -replace '(")(http://fhir.org/guides/nachc/hiv-cds/ValueSet/nachc-[^"]*)(\")', ('"' + '$2' + '.json"') |
             Set-Content $_
    }

echo "Cleaning up .json suffixes..."
get-childitem ./ -recurse -include *.json | 
  select -expand fullname |
    foreach {
            (Get-Content $_) -replace '\.json\.json', ('.json') |
             Set-Content $_
    }

echo "Updating valuesete urls..."
get-childitem ./ -recurse -include *.json | 
  select -expand fullname |
    foreach {
            (Get-Content $_) -replace 'http://fhir.org/guides/nachc/hiv-cds/ValueSet/', ('https://nachc-cad.github.io/hiv-cds/resources/hiv-cds/valueset/generated/') |
             Set-Content $_
    }

echo "Updating valuesete urls..."
get-childitem ./ -recurse -include *.json | 
  select -expand fullname |
    foreach {
            (Get-Content $_) -replace 'https://nachc-cad.github.io/hiv-cds/resources/hiv-cds/valueset/generated/nachc', ('https://nachc-cad.github.io/hiv-cds/resources/hiv-cds/valueset/generated/valueset-nachc') |
             Set-Content $_
    }

echo "Updating valuesete urls..."
get-childitem ./ -recurse -include *.json | 
  select -expand fullname |
    foreach {
            (Get-Content $_) -replace 'https://nachc-cad.github.io/hiv-cds/resources/hiv-cds/valueset/generated/', ('https://nachc-cad.github.io/hiv-cds/resources/hiv-cds/vocabulary/valueset/generated/') |
             Set-Content $_
    }

echo "Done."


