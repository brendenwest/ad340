package com.brisksoft.ad340

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity

class Movie {

    // 2D data array
    companion object {
        val movies = arrayOf(
            arrayOf(
                "Night of the Comet",
                "1984",
                "",
                "https://cdn.collider.com/wp-content/uploads/2016/10/night-of-comet.jpg",
                "What would kids in the 1980s do if the apocalypse blew through the world without them noticing? Hang out at the mall, but of course. That’s the set-up for this very funny, quite dated horror-comedy, which begins when a quartet of adolescents lock themselves inside a projection booth at the mall’s multiplex. This somehow allows them to live through an extinction level event of some sort, which has also left roaming bands of murderous mutants. Catherine Mary Stewart of the equally inexplicable Weekend at Bernie’s leads the film, but it’s a movie of mood more than substance ultimately. Does the wealth-fueled naiveté of the average white teenager survive in a vacuum? Does it go away when they are being hunted for sustenance? It’s an interesting to watch on these terms and when the zombies show up, director Thom Eberhardt adds menace and a tight feel for suspense to the action sequences. And if we’re being honest, it belongs on this list for its soundtrack alone. The rest of this is just whip cream and cherries. – Chris Cabin"
            ),
            arrayOf(
                "Cemetery Man",
                "2004",
                "Michele Soavi",
                "https://cdn.collider.com/wp-content/uploads/2017/10/cemetary-man.jpg",
                "Directed by Dario Argento protegeMichele Soavi, Cemetery Man (or Dellamorte Dellamore) is a weird, wild head trip of a movie that treats the living dead as more of a nuisance than a deadly threat. Based on the comic series Dylan Dog, Cemetery Man stars Everett as Francesco Dellamorte, a misanthropic gravedigger who prefers the company of the dead to the living. And why wouldn’t he? The living are assholes and they keep spreading rumors he’s impotent. There’s just one catch — the dead won’t stay burried in his graveyard. When he meets a stunning widow (Falchi) at her husband’s funeral, Dellamorte falls head over heels, courts her in the morbid halls of his ossuary, and before you know it, they’re stripped naked and steaming it up on top of her dead husband’s grave. That’s just the start of things getting weird. \nDellamorte descends into madness, and the further he falls the more Cemetery Man threatens to go off the rails, leaving logic behind in favor of a slipstream psychosis. The result is a bit of a mess without a plot to speak of, but a gloriously weird mess it is. Saturated with philosophy and offbeat humor, Cemetary Man is all about sex and death, friendship and deception; a surrealist, satirical and stylish trip to the brink loaded with splendid visuals and a knockout performance from Everett that takes him from a strapping hero to spitting psychopath. — Haleigh Foutch"
            ),
            arrayOf(
                "Dead Snow",
                "2009",
                "Tommy Wirkola",
                "https://cdn.collider.com/wp-content/uploads/2016/07/dead-snow.jpg",
                "With so many zombie movies over the years, eventually you’re going to run out of ways to freshen up the sub-genre. Enter Wirkola’s decidedly skewed take on zombies in this horror-comedy with plenty of guts. Sure, zombies are great movie monsters, but if you have Nazi zombies, well you’ve just doubled-down on the level of villainy (and pun-worthiness) in your picture! \nThis splatter-fest puts a Nordic spin on the traditional zombie by adding in elements of the Draugr, an undead creature from Scandinavian folklore that fiercely protects its treasure horde. In the case of Dead Snow, these draugr happen to be former SS soldiers who terrorized a Norwegian town and looted their belongings, only to be done in or chased into the freezing mountains by the villagers themselves. Dead Snow gets originality points for this, for sure. It’s also a very funny, gory, and satisfyingly violent movie with elements of Evil Dead and “teen sex/slasher” flicks scattered throughout. And if you like it, there’s more where that came from in the sequel, Dead Snow: Red vs Dead. – Dave Trumbore"
            ),
            arrayOf(
                "28 Weeks Later",
                "2007",
                "Juan Carlos Fresnadillo",
                "https://cdn.collider.com/wp-content/uploads/2015/08/28-weeks-later.jpg",
                "28 Weeks Later is one of those rare sequels that does the original proud, especially when the original is a film as acclaimed and influential as 28 Days Later. Director Juan Carlos Fresnadillo made his English-language directorial debut on the sequel, stepping in for Danny Boyle, and pulled off a fantastic trick in honoring the “franchise style” Boyle established in the original — the quick edits and snarling infected — while evolving it and adding his own visual flourish to the mix. \n 28 Days Later subverted the conventions of the zombie genre in such clever, convincing ways, it became the modern day zombie template that countless films tried to mimic. 28 Weeks Later was smart enough not to follow the blueprint and flipped the script, depicting the British government’s attempt to rebuild society in the aftermath of the rage virus and the subsequent outbreak that brings it all crashing down. Through the contained military facility we get to witness a small-scale version of the viral apocalypse that we missed in the first film and the desperate, hopeless attempts to stop it. That makes 28 Weeks Later is a bit more of a conventional zombie film, depicting the downfall of society and the breakdown of boundaries in times terror, but it’s a very good conventional zombie movie. Fresnadillo hits all the right notes, lacing the broad arc with intimate family drama and depending on his superb cast to sell every moment of heartbreak amidst the bloodshed. — Haleigh Foutch"
            ),
            arrayOf(
                "Night of the Creeps",
                "1986",
                "Fred Dekker",
                "https://cdn.collider.com/wp-content/uploads/2017/10/night-of-the-creeps.jpg",
                "The delightfully delirious directorial debut from Monster Squad helmer Fred Dekker, Night of the Creeps is a loving tribute to the zombie genre that’s as packed to the brim with self-reference as it is with cheeky, cheesy fun. The film follows two college boys trying to land a spot in a fraternity in the name of scoring chicks. To earn their initiation, the boys have to sneak into the college medical center, where they discover the long-frozen corpse of a 1950s coed with alien slugs coursing through his brain. Hijinks follow, the body thaws, and space parasites are unleashed on campus, transforming their hosts into mindless zombies. \nA blunt-force display of Dekker’s sensibilities, Night of the Creeps is an exuberant blend of zombie genre trappings and the sci-fi B-movies of yore; like Mars Attacks by way of Night of the Living Dead. Dekker lines his film with loving references to the genre, most obviously with his characters, who he names after the horror greats: Romero, Raimi, Carpenter, Cronenberg, Cameron, Landis, and Hooper. Night of the Creeps feels like Dekker took all his favorite movies and stirred them together in a silly, slimy stew. It can be clunky and goofy, but Night of the Creeps wears its idol worship like a badge of honor and Dekker’s creative flourish is a firewall that keeps his homage from becoming derivative. — Haleigh Foutch"
            ),
            arrayOf(
                "ParaNorman",
                "2012",
                "Chris Butler, Sam Fell",
                "https://cdn.collider.com/wp-content/uploads/paranorman-movie-image.jpg",
                "Rarely do zombies get the animated treatment (rarer still, stop-motion animation), and even if they do, they’re traditionally made the villains. LAIKA is anything but traditional, which makes their films so endearing, unique, and memorable. ParaNorman, one of the stop-motion studio’s handful of original films, manages to not only (re)animate some truly gruesome and decaying corpses, but to give them a voice and agency within the story. Most live-action movies can’t even achieve that much. \nBut what truly makes ParaNorman a great zombie tale is that the zombies themselves are more than just part of the spooky story (along with witches, ghosts, and dark magic), they’re a similar stand-in for societal problems first addressed by Romero’s original undead flick. Without giving away too many spoilers, the zombies themselves are reanimated townsfolk from colonial times who have realized the error of their ways but are prevented from setting things right thanks to a witch’s curse. Because they can’t communicate, they’re set upon by an angry mob. While you’d expect that turn of events in a traditional monster movie, the twist in ParaNorman is what lends some substance to its overall message. As a bonus, it’s a zombie movie you can watch with the kids! – Dave Trumbore"
            ),
            arrayOf(
                "Zombieland",
                "2009",
                "Ruben Fleischer",
                "https://static1.colliderimages.com/wordpress/wp-content/uploads/2020/10/zombieland-jesse-eisenberg-social-feature.jpg?q=50&fit=crop&w=750&dpr=1.5",
                "One of the greatest enjoyments of horror cinema in the last few decades has been watching filmmakers who grew up knowing the rules of the genre find new and exciting ways to subvert them. Shaun of the Dead is the gold star of self-referential cinematic love letters, but Ruben Fleischer’s Zombieland is a rollicking comedy horror in its own right.\nZombieland arrived in theaters in 2009, towards the end of a new zombie boom, and it’s a film made for audiences who already know the rules and want to have some fun playing the game. The script comes from Deadpool screenwriters Reese and Wernick, and both properties share the duo’s knack for genre deconstruction and razor-sharp, smart-mouthed humor. The ensemble comedic performers has a blast doling out verbal beatdowns in between actually beating down the undead. And let’s be honest — even if Zombieland wasn’t an all-around fun and entertaining action horror, it deserves a spot on the list for giving BIll Murray the most Bill Murray cameo of all time. — Haleigh Foutch"
            ),
            arrayOf(
                "Planet Terror",
                "2007",
                "Robert Rodriguez",
                "https://cdn.collider.com/wp-content/uploads/rose_mcgowan_and_marley_shelton_star_in_robert_rodriquez_s_planet_terror_grindhouse_s.jpg",
                "In the tradition of Romero, modern Zombie films have become known as the home of sharp social commentary and forward-thinking humanism. You won’t find any of that in Planet Terror. Initially released as one half of the Robert Rodriguez/Quentin Tarantino double feature Grindhouse, Planet Terror was initially dismissed by critics as the lesser of the two entries, but time has proven it to be a raucous, endlessly rewatchable, and consummately reprobate entry to the zombie genre.Written and directed by Rodriguez (though cast interviews revealed that the directors collaborated freely on both pictures), Planet Terror is cheeky, free-wheeling, and delighted with its own depravity as it employs the shield of grindhouse tropes to hack through horror taboos from child death to testicular violence.\nBorrowing heavily from the exploitation aesthetic with the kind of budget its forbears could only dream of, the film stars Rose McGowan as Cherry Darling, a brassy go-go dancer who finds herself in the midst of the apocalypse with a rag-tag band of survivors — played by an A+ ensemble of underrated actors who finally get to play the leading roles they’ve always deserved. Flesh-hungry humanoid mutants tear through the Texas countryside, leaving a gooey trail of body parts in their wake. In short order, Cherry winds up with a machine gun for a leg, as you do, and the film boils over into a chaotic free-for-all of bloodshed and grotesqueries. It’s a blast and it triumphs because it leans in so hard. Just look at the “missing reel”  in the second act, which skips everybody’s least favorite part of a zombie movie and jumps right into the climactic third act. And that’s Planet Terror in a nutshell; audacious, goofy and always going right for the guts. — Haleigh Foutch"
            ),
            arrayOf(
                "Train to Busan",
                "2016",
                "Sang-ho Yen",
                "https://cdn.collider.com/wp-content/uploads/2016/12/train-to-busan-movie-image.jpg",
                "After the zombie genre got a big boost in the early aughts, the living dead thrived on serialized television but they died off in cinemas for a while. Train to Busan is a proper return to form for the genre, an old-fashioned zombie drama with heart and soul, a simple but clever set-up and some scary af zombies. The film follows a father and his young daughter on a terrifying train ride that sends them speeding through a zombie outbreak in South Korea, trapped inside increasingly infected compartments of the passenger train. Filled with characters you root for —  and some you love to root against — Train to Busan is packed with zombie action that uses the tight quarters to thrilling effect, traveling through the cars of the train with a series of imaginative set-pieces that put the physicality of these contorted, fast-moving zombies to great effect. After watching the living survive among the dead for years on the silver screen, it’s damn well time for someone to give the undead their bite back and Train to Busan is just the ticket. — Haleigh Foutch"
            ),
            arrayOf(
                "The Beyond",
                "1981",
                "Lucio Fulci",
                "https://cdn.collider.com/wp-content/uploads/2017/10/the-beyond.jpg",
                "After making his answer to the Romero school of zombie cinema with Zombi 2, Italian horror maestro Lucio Fulci took the idea of the undead and got weird with it in his unofficial “Gates of Hell” trilogy; City of the Living Dead, The Beyond, and House by the Cemetary. The Beyond has proven the most enduring of the lot, and for good reason, it’s a hypnotic oddity, as unsettling as it is incoherent.\nIf you walk into The Beyond assuming the plot matters (it really doesn’t), the film follows a young woman (MacColl), who inherits a Louisianna hotel that, bad news, happens to be built on one of the gates to hell. In between the eye-gouging, acid face-melting, and all manner of blood and guts Fulci conjures up for his ghastly visions (The Beyond isn’t quite as gory as Zombi 2, but it’s close)., there’s also a brewing metaphysical dread, a sense of doom that bubbles up to a screaming boil in the film’s final reveal. — Haleigh Foutch"
            ),
            arrayOf(
                "Day of the Dead",
                "1985",
                "George Romero",
                "https://cdn.collider.com/wp-content/uploads/2017/10/day-of-the-dead.jpg",
                "The concluding chapter in Romero’s original “Dead” trilogy, Day of the Dead has never found the frenzied fans of its two predecessors. In fact, it’s often been met with some harsh criticisms, which is unfortunate because it’s a staggering zombie film in its own right. Perhaps its the idea of sentient zombies, a tenet of Romero’s later “Dead” films introduced in Day of the Dead via Bub, the loveable flesh-hungry fiend who begins to show signs of cognizance during military testing. Or maybe it’s the script, which turns up the volume on Romero’s trademark cultural critique until the skewering tips over into preachy territory.\nBut here’s the thing, while other filmmakers may have been happy to recreate the formula that worked for them in the past, Romero consistently evolved his living dead films, and Day of the Dead was the boldest of them all. Set on a military base, Romero gets downright political, asking hard questions about power and how much any one organization should or can ever have. It’s a pensive film, not quite as primal as Night of the Living Dead and nowhere near as funny as Dawn of the Dead, which makes it a slow watch. But hoo boy, if you came for zombie gore, is the payoff rewarding. Day of the Dead has some of the most stomach-churning, sticky practical effects in the history of horror, practically painting the sterile military base red in the final act.  — Haleigh Foutch"
            ),
            arrayOf(
                "The Serpent & the Rainbow",
                "1988",
                "Wes Craven",
                "https://cdn.collider.com/wp-content/uploads/2017/10/the-serpent-and-the-rainbow.jpg",
                "Wes Craven heads to Haiti for this haunting tale of voodoo possession, with Bill Pullman out front as the superstitious American studying a case of possession that turns people into zombies. Away from the suburbs of A Nightmare on Elm Street or (wince) Deadly Friend, Craven evinces a new visual and auditory flavor of terror, such as in the deeply unsettling burial scenes. Craven is more spare and direct with his fantastical touches here, and rather than use foreign lands as solely a place of dangerous mystical forces, he engenders a fascination with the setting, the people, and the history in a genre not always known for caring for much more than a big body count. It’s ultimately a classic tale of a cynic being faced with the horrors and glories of faith, though, let’s be honest, Craven seems far more interested in the horrors. – Chris Cabin"
            ),
            arrayOf(
                "Re-Animator",
                "1985",
                "Stuart Gordon",
                "https://cdn.collider.com/wp-content/uploads/2016/09/re-animator.jpg",
                "There just are not enough quality movie adaptations of H.P. Lovecraft’s works out there today. One of the best happens to be this tale, taken from Lovecraft’s early-1920s horror short, “Herbert West–Reanimator.” This tale was one of the first instances of zombies as scientifically reanimated corpses driven by primal needs. Re-Animator certainly takes that premise to the extreme in the film that launched a trilogy and became a cult classic.\nCentering on Herbert West, a medical student of questionable morals who’s developed a re-animating serum, Re-Animator is a tongue-in-cheek zombie film that is not afraid to push the envelope. Zombie cats and a reanimated severed head, violation of corpses in the morgue, gruesome deaths by decapitation and bone-saw, sexual assault by zombie, and a rather gory final battle. It’s definitely not for the faint of heart of easily offended, but for the completionists among you, it’s a must-watch. (There is an R-rated cut, though Gordon himself prefers the unrated version if that helps you decide one way or the other.) -  Dave Trumbore"
            ),
            arrayOf(
                "Zombi 2",
                "1971",
                "Lucio Fulci",
                "https://cdn.collider.com/wp-content/uploads/2016/10/zombi-2.jpg",
                "Zombie vs. Shark. That’s really all I should have to say. Zombi 2 (Titled Zombie in America) isn’t really a sequel. In a marketing gimmick, it was billed as a semi-sequel to George Romero‘s Dawn of the Dead (titled Zombi in Italy), but beyond spectacular zombie effects, the two actually have very little in common, narratively or tonally. Zombi 2 follows Anne Bowles (Farrow), a young woman who heads off to a remote island to help her ailing father, unaware that the land is under a voodoo curse that brings the dead back to life.\nHelmed by Giallo maestro Lucio Fulci, Zombi 2 brings none of the social commentary or nuanced character drama of its marketed predecessor, but what it lacks in pedigree, it makes up for in stylistic panache and first-rate zombie action. Fulci is no stranger to the zombie genre, but Zombi 2 was his most conventional approach to the living dead before he got veered in a paranormal and interdimensional with his unofficial trilogy City of the Living Dead, The Beyond, and The House by The Cemetery. Zombi 2 gives all its love to set pieces and practical effects and it never aspires to be much more than zombie trash. However, it’s the very best trash — top of the heap — and it boasts some of the most inventive and flawlessly rendered zombie set pieces of all time (zombie vs shark is the best, but it isn’t the only). Zombi 2 isn’t deep, but swims along brilliantly in the shallows. — Haleigh Foutch"
            ),
            arrayOf(
                "Dawn of the Dead",
                "2004",
                "Zack Snyder",
                "https://cdn.collider.com/wp-content/uploads/2016/10/dawn-of-the-dead.jpg",
                "These days, Zack Snyder is known for being the architect of Warner Bros.’ DC Comics live-action movie universe, but it was not always that way. Before Man of Steel, Watchmen, or even his claim to fame 300, Snyder turned in a terrifying modernized account of Romero’s 1978 original by the same name. (Oddly enough, Marvel movie-maker James Gunn also contributed his screenwriting talent to this film.)\nThe most memorable aspect of Snyder’s version? Fast zombies. They’re terrifying. Sure, it wasn’t the first film to implement the twist on Romero’s modern zombie, but Snyder’s take on the fast zombies were quite the surprise since they appeared in the “Night of the Living Dead” universe. By the time that Dawn of the Dead had arrived in theaters, zombie movie fans thought they had seen enough to prepare themselves for “what they would do” should the zombie apocalypse come to pass. Once Snyder’s first fast zombie ran after its living meal, those plans went out the window. I also have a soft spot for the baby zombie and the fat woman in the wheelbarrow (played by Chris Farley’s Tommy Boy stunt double Ermes Blarasin), as well as the fantastic and surprisingly capable cast assembled for this film. If you’ve written this one off or have never seen it, definitely put it on your list. – Dave Trumbore"
            ),
            arrayOf(
                "Return of the Living Dead",
                "1985",
                "Dan O'Bannon",
                "https://cdn.collider.com/wp-content/uploads/return_of_the_living_dead_movie_image_01.jpg",
                "Blood, boobs, and braaaaaiiiiins; The Return of the Living Dead is the triple threat of zombie B-movies and it’s without a doubt the most fun you can have hanging with the undead. After a pair of fumbling medical warehouse employees accidentally unleash a toxic gas on a nearby cemetery, the dead return to life in ghoulish, grizzly fashion with an insatiable hunger for brains.\nThe directorial debut from Alien and Total Recall screenwriter Dan O’Bannon, Return of the Living Dead is a zombie film and a party rolled into one mud-smeared, punk rock romp. Long before the meta-horror trend, Return of the Living dead name-dropped its inspiration openly, casually referencing Night of the Living Dead without abandon. It’s an outrageous film from start to finish, including iconic moments like Linnea Quigley’s nude grave dancing, a morose talking zombie, and slimy, gory creature effects that make you want to take a shower. Return of the Living Dead is over-the-top and gleefully tongue-in-cheek, like an E.C. comic come to life, a hard-rocking response to Romero’s “Living Dead” films that has become a classic in its own right.– Haleigh Foutch"
            ),
            arrayOf(
                "Dead Alive",
                "1992",
                "Peter Jackson",
                "https://cdn.collider.com/wp-content/uploads/2016/04/dead-alive.jpg",
                "Peter Jackson could have only made this gory, gushy, and occasionally outright repulsive zombie film, and he would still be a kind of legend, if not at the level of the man who brought Lord of the Rings to the big-screen. Dead Alive openly toys with one of horror’s most cherished concepts – repression – and when Lionel’s (Balme) love for a local girl is no longer held down by his controlling mother (Moody), out come the decaying zombie-like creatures to act as a horrifying expression of momma’s villainous control. Like Tobe Hooper and Stuart Gordon’s iconic 1980s output, Dead Alive (also known as Braindead) strives for what Hooper called “red humor,” a melding of slapstick and physical comedy with horror, and the result is the most idiosyncratic and zany effort that Jackson produced, complete with zombie-monster momma and rotted ears and noses garnishing a nice Sunday chowder. — Chris Cabin"
            ),
            arrayOf(
                "28 Days Later",
                "2002",
                "Danny Boyle",
                "https://cdn.collider.com/wp-content/uploads/2016/10/28-days-later.jpg",
                "28 Days Later redefined the zombie aesthetic for a generation of filmmakers. Directed by Oscar-winner Danny Boyle from a script by brilliant sci-fi scribe Alex Garland, 28 Days Later rubbed zombie purists the wrong way at first. For one thing, they weren’t technically zombies but humans infected with a feral rage virus, and as a result, they moved way too fast. Purists scoffed, but audiences around the world discovered a new approach to the beloved genre that has not only endured but become one of the most influential modern entries in the genre.\nBoyle crafted a new image of the viral apocalypse, a savage, bloodthirsty virus that sweeps through the world leaving a broken, infested shell behind. That’s what Jim (Murphy) awakes to find, rising from a coma to discover England in ashes. Boyle captures the chaos and the terror of a world destroyed with his signature stylistic flourishes — quick edits and skewed color palettes that enhance the adrenaline-fuelled fear that engulfs the first act. From there, the film settles into an intimate character drama set against the backdrop of collapsed society and the fringe groups that try to hold on to their sense of normalcy.  28 Days Later is so successful because it is as tender as it is terrifying, matching moments of horror with humanity, and a healthy spattering of cultural commentary along the way. — Haleigh Foutch"
            ),
            arrayOf(
                "Night of the Living Dead",
                "1968",
                "George Romero",
                "https://cdn.collider.com/wp-content/uploads/2015/10/night-of-the-living-dead.jpg",
                "Night of the Living Dead is not the first zombie movie; far from it. But it’s the first to leave traditional voodoo roots behind, move beyond using atomic radiation (more or less), and do away with alien invasions to give us the modern version of zombies as reanimated corpses who hunger for flesh. For the last 50 years and counting, that’s the model that zombie stories in all media have been built from, including Romero’s own sequels to his seminal film.\nAnd yet the original still holds up today, not because of dazzling special effects or incredible acting but because of the social themes Night of the Living Dead attempted to skewer. The undercurrents of racial prejudice, vigilantism, mob mentality, and cowardice in the face of difficulty continue to hit hard 50 years later since these themes are common human attributes throughout history that we’re still attempting to overcome today. In subverting everything that defines life as a human, Romero was able to explore humanity at its least humane. Often imitated, rarely duplicated, Night of the Living Dead remains a contemporary horror classic. – Dave Trumbore"
            ),
            arrayOf(
                "Shawn of the Dead",
                "2004",
                "Edgar Wright",
                "https://cdn.collider.com/wp-content/uploads/2014/10/shaun-of-the-dead-image.jpg",
                "When it comes to advancing the zombie movie genre, this film stands out among the crowd. It’s both a warm-blooded love letter to the history of zombie films and a stone-cold laugher that takes shots at those very same films. It helped quite a bit that Wright and Pegg enjoyed a shorthand from working together on the TV series Is It Bill Bailey? and Spaced before collaborating on what is arguably the best zombie comedy out there today.\nShaun of the Dead didn’t just take the zombie movie tropes and run with them, it allowed Wright’s meticulous attention to detail and brand of humor, complemented by Pegg’s delivery, to bubble to the surface. It’s one of the most re-watchable movies out there because you’ll always find something new that you may have missed dozens of times before. And once you’ve steeped yourself in the history of zombie films, you should also find that you have a new appreciation for just how lovingly Shaun of the Dead was put together. – Dave Trumbore"
            ),
            arrayOf(
                "Dawn of the Dead",
                "1978",
                "George Romero",
                "https://cdn.collider.com/wp-content/uploads/2015/08/dawn-of-the-dead-romero.jpg",
                "George Romero didn’t invent the zombie, but he did single-handedly create the template for the modern zombie movie as we know it with his exquisite Night of the Living Dead. For his sequel, Romero dodged the temptation to retread familiar territory (a quality he would maintain for each of his subsequent “dead” films), ditching the intimate confines of a home for the sprawling reaches of a shopping mall, and trading his black-and-white bleakness for a playful color-saturated palette.\nDawn of the Dead is a horror sequel in every sense, bigger and bloodier, but it maintain’s Romero’s commitment to piercing social commentary, this time tackling the insatiable lust of American consumerism. It’s also packed to the brim with Romero’s skilled eye for visceral violence rendered with first-rate old-school gore effects from Tom Savini, the legendary craftsman of carnage who transplanted his experience as a combat photographer in Vietnam to a career spent creating on-screen nightmares. As in all of Romero’s great work, that beautifully executed bloodshed is only a backdrop for a compelling character drama as the group of strangers seeking refuge in the abandoned shopping complex cope with increasing interpersonal conflict. Romero directs it all with wit and empathy, and an expert eye for when to drop the next big scare. — Haleigh Foutch"
            )
        )
    }

}
