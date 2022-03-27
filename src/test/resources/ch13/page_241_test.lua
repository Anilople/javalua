-- page 241
function div0(a, b)
    if b == 0 then
        error("DIV BY ZERO !")
    else
        return a / b
    end
end

function div1(a, b) return div0(a, b) end
function div2(a, b) return div1(a, b) end

ok, result = pcall(div2, 4, 2);
-- true    2.0
print(ok, result)

ok, err = pcall(div2, 5, 0);
-- false   page_241_test.lua:4: DIV BY ZERO !
print(ok, err)

ok, err = pcall(div2, {}, {});
-- false   page_241_test.lua:6: attempt to perform arithmetic on a table value (local 'a')
print(ok, err)
